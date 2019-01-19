package com.devakt.service;

import com.devakt.entity.Booking;
import com.devakt.entity.Room;
import com.devakt.entity.User;
import com.devakt.exception.BookException;
import com.devakt.exception.LoginException;
import com.devakt.exception.RoomNotFoundException;
import com.devakt.mapper.BookingMapper;
import com.devakt.mapper.RoomMapper;
import com.devakt.modal.BookingView;
import com.devakt.modal.IntervalView;
import com.devakt.modal.RoomView;
import com.devakt.repository.BookingRepository;
import com.devakt.repository.RoomRepository;
import static com.devakt.utils.DateUtils.dateToInterval;
import static com.devakt.utils.DateUtils.intervalToDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.devakt.repository.UserRepository;
import static com.devakt.utils.ParamUtils.INTERVAL_START;
import static com.devakt.utils.ParamUtils.INTERVAL_END;

import com.devakt.utils.IntegerUtils;
import com.devakt.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public List<RoomView> getRooms() {

        return roomRepository
            .findAll()
            .stream()
            .map(RoomMapper.INSTANCE::roomToRoomView)
            .collect(Collectors.toList());

    }

    public RoomView getRoom(int roomId) {
        return roomRepository.findById(roomId)
            .map(RoomMapper.INSTANCE::roomToRoomView)
            .orElseThrow(RoomNotFoundException::new);

    }

    public List<BookingView> getRoomBookings(int roomId) {

        return bookingRepository
                .findByRoomIdAndActive(roomId, true)
                .stream()
                .map(BookingMapper.INSTANCE::bookingToBookingView)
                .collect(Collectors.toList());
    }

    public List<BookingView> getBookings(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(LoginException::new);

        return bookingRepository
                .findByUserId(user.getId())
                .stream()
                .filter(b -> StringUtils.equals(b.getUser().getEmail(), email))
                .map(BookingMapper.INSTANCE::bookingToBookingView)
                .collect(Collectors.toList());
    }

    public List<RoomView> getAvailableRooms(String bookingDate, int nbPersons, String fromTime, String toTime) {

        int intervalFrom = Integer.parseInt(fromTime);
        int intervalTo = Integer.parseInt(toTime);

        if(intervalFrom < INTERVAL_START || intervalTo > INTERVAL_END) {
            return Collections.emptyList();
        }

        Map<Integer, List<Booking>> bookingsMap  =
                bookingRepository.findByBookingDateAndActive(bookingDate, true)
                        .stream()
                        .collect(Collectors.groupingBy(b -> b.getRoom().getId(), Collectors.toList()));

        List<Integer> userInterval = IntStream.range(intervalFrom, intervalTo).boxed().collect(Collectors.toList());

        List<RoomView> rooms = new ArrayList<>();

        for(Room room : roomRepository.findAll()) {
            List<Booking> bookings = bookingsMap.get(room.getId());
            if (verifyRoomAvailability(room, nbPersons, bookings, userInterval)) {
                rooms.add(RoomMapper.INSTANCE.roomToRoomView(room));
            }
        }

        return rooms;
    }

    public List<IntervalView> getIntervals () {
        return IntStream.rangeClosed(INTERVAL_START, INTERVAL_END)
                .boxed()
                .map(i -> IntervalView.builder()
                        .fromTime(intervalToDate(i))
                        .fromTimeInt(i)
                        .toTime(intervalToDate(i+1))
                        .toTimeInt(i+1)
                        .build())
                .collect(Collectors.toList());
    }

    public void book(BookingView bookingView) {
        User user = userRepository.findByEmail(bookingView.getEmail())
                .orElseThrow(LoginException::new);
        Room room = roomRepository.findById(bookingView.getRoomId())
                .orElseThrow(RoomNotFoundException::new);

        if(!verifyRoomAvailability(room.getId(), bookingView)) {
            throw new BookException();
        }

        Booking booking = BookingMapper.INSTANCE.bookingViewToBooking(bookingView);
        booking.setUser(user);
        booking.setRoom(room);
        booking.setActive(true);
        bookingRepository.save(booking);
    }

    public List<IntervalView> getAvailableIntervals(int roomId, String bookingDate) {

        List<Integer> reservedInterval  = bookingRepository
                .findByBookingDateAndRoomIdAndActive(bookingDate, roomId, true)
                .stream()
                .flatMap(b -> IntStream.range(dateToInterval(b.getFromTime()), dateToInterval(b.getToTime())).boxed())
                .collect(Collectors.toList());

        List<Integer> allIntervals = IntStream.rangeClosed(INTERVAL_START, INTERVAL_END).boxed().collect(Collectors.toList());

        return allIntervals.stream()
                .filter(i -> !reservedInterval.contains(i))
                .map(i -> IntervalView.builder().bookingDate(bookingDate).fromTime(intervalToDate(i)).toTime(intervalToDate(i+1)).build())
                .collect(Collectors.toList());

    }

    public void removeBooking(ObjectId bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(RoomNotFoundException::new);
        booking.setRemoveDate(new Date());
        booking.setActive(false);
        bookingRepository.save(booking);
    }

    private boolean verifyRoomAvailability(Integer roomId, BookingView bookingView) {

        List<Booking> bookings  =
                bookingRepository.findByBookingDateAndActive(bookingView.getBookingDate(), true)
                        .stream()
                        .filter(b -> IntegerUtils.equals(b.getRoom().getId(), roomId))
                        .collect(Collectors.toList());

        List<Integer> userInterval = IntStream.range(bookingView.getFromTime(), bookingView.getToTime())
                .boxed().collect(Collectors.toList());

        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);

        return verifyRoomAvailability(room, bookingView.getNbPersons(), bookings, userInterval);

    }

    private boolean verifyRoomAvailability (Room room, int nbPersons, List<Booking> bookings, List<Integer> userInterval) {

        if(room.getNbPersons() < nbPersons) {
            return false;
        }

        if(bookings == null) {
            return true;
        }

        List<Integer> bookedIntervals = bookings.stream()
                .flatMap(b -> IntStream.range(dateToInterval(b.getFromTime()), dateToInterval(b.getToTime())).boxed())
                .collect(Collectors.toList());

        return userInterval.stream().noneMatch(bookedIntervals::contains);
    }

}
