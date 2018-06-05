package com.devakt.service;

import com.devakt.domain.Interval;
import com.devakt.entity.Booking;
import com.devakt.entity.Room;
import com.devakt.entity.User;
import com.devakt.exception.LoginException;
import com.devakt.exception.RoomNotFoundException;
import com.devakt.mapper.BookingMapper;
import com.devakt.mapper.RoomMapper;
import com.devakt.modal.BookingView;
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
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.interval.start}")
    private int intervalStart;

    @Value("${app.interval.end}")
    private int intervalEnd;

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
                .filter(b -> b.getUser().getEmail().equals(email))
                .map(BookingMapper.INSTANCE::bookingToBookingView)
                .collect(Collectors.toList());
    }

    public List<RoomView> getAvailableRooms(String bookingDate, int nbPersons, String fromTime, String toTime) {

        List<RoomView> rooms = new ArrayList<>();

        Map<Integer, List<Booking>> bookingsMap  =
            bookingRepository.findByBookingDateAndActive(bookingDate, true)
            .stream()
            .collect(Collectors.groupingBy(b -> b.getRoom().getId(), Collectors.toList()));

        int intervalFrom = Integer.parseInt(fromTime);
        int intervalTo = Integer.parseInt(toTime);

        if(intervalFrom < intervalStart || intervalTo > intervalEnd) {
            return Collections.emptyList();
        }

        List<Integer> userInterval = IntStream.range(intervalFrom, intervalTo).boxed().collect(Collectors.toList());

        for(Room room : roomRepository.findAll()) {
            List<Booking> bookings = bookingsMap.get(room.getId());
            if(room.getNbPersons() >= nbPersons) {
                if(bookings == null) {
                    rooms.add(RoomMapper.INSTANCE.roomToRoomView(room));
                } else {
                    List<Integer> bookedIntervals = bookings.stream()
                        .flatMap(b -> IntStream.range(dateToInterval(b.getFromTime()), dateToInterval(b.getToTime())).boxed())
                        .collect(Collectors.toList());
                    if(userInterval.stream().noneMatch(bookedIntervals::contains)) {
                        rooms.add(RoomMapper.INSTANCE.roomToRoomView(room));
                    }
                }
            }
        }

        return rooms;
    }

    public List<Interval> getIntervals () {
        return IntStream.rangeClosed(intervalStart, intervalEnd)
                .boxed()
                .map(i -> Interval.builder()
                        .fromTime(intervalToDate(i))
                        .fromTimeInt(i)
                        .toTime(intervalToDate(i+1))
                        .toTimeInt(i+1)
                        .build())
                .collect(Collectors.toList());
    }

    public void book(BookingView bookingView) {
        Booking booking = BookingMapper.INSTANCE.bookingViewToBooking(bookingView);
        User user = userRepository.findByEmail(bookingView.getEmail())
                .orElseThrow(LoginException::new);
        Room room = roomRepository.findById(bookingView.getRoomId())
                .orElseThrow(RoomNotFoundException::new);
        booking.setUser(user);
        booking.setRoom(room);
        booking.setActive(true);
        bookingRepository.save(booking);
    }

    public List<Interval> getAvailableIntervals(int roomId, String bookingDate) {

        List<Integer> reservedInterval  = bookingRepository
                .findByBookingDateAndRoomIdAndActive(bookingDate, roomId, true)
                .stream()
                .flatMap(b -> IntStream.range(dateToInterval(b.getFromTime()), dateToInterval(b.getToTime())).boxed())
                .collect(Collectors.toList());

        List<Integer> allIntervals = IntStream.rangeClosed(intervalStart, intervalEnd).boxed().collect(Collectors.toList());

        return allIntervals.stream()
                .filter(i -> !reservedInterval.contains(i))
                .map(i -> Interval.builder().bookingDate(bookingDate).fromTime(intervalToDate(i)).toTime(intervalToDate(i+1)).build())
                .collect(Collectors.toList());

    }

    public void removeBooking(ObjectId bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(RoomNotFoundException::new);
        booking.setRemoveDate(new Date());
        booking.setActive(false);
        bookingRepository.save(booking);
    }
}
