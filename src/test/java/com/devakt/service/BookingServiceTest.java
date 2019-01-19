package com.devakt.service;

import static com.devakt.builder.ObjectBuilder.buildRoom;
import static com.devakt.builder.ObjectBuilder.buildBooking;

import com.devakt.entity.Booking;
import com.devakt.modal.RoomView;
import com.devakt.repository.BookingRepository;
import com.devakt.repository.RoomRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    /*
    @Before
    public void setUp() {
        ReflectionTestUtils.setField(bookingService, "intervalStart", 30);
        ReflectionTestUtils.setField(bookingService, "intervalEnd", 79);
    }

    @Test
    public void testGetRooms() {
        when(roomRepository.findAll()).thenReturn(Arrays.asList(buildRoom(1,4), buildRoom(2,8)));
        List<RoomView> rooms = bookingService.getRooms();
        assertThat(rooms.get(0).getNbPersons()).isEqualTo(4);
        assertThat(rooms.get(1).getNbPersons()).isEqualTo(8);
    }

    @Test
    public void testGetAvailableRooms() {
        Booking booking1 = buildBooking(1,"2018-06-19", "10:00", "12:00");
        Booking booking2 = buildBooking(1,"2018-06-19", "13:00", "14:00");
        Booking booking3 = buildBooking(2,"2018-06-19", "10:00", "13:00");
        Booking booking4 = buildBooking(2,"2018-06-19", "13:00", "14:00");
        when(bookingRepository.findByBookingDateAndActive(anyString(), anyBoolean())).thenReturn(Arrays.asList(booking1, booking2, booking3, booking4));
        when(roomRepository.findById(1)).thenReturn(Optional.of(buildRoom(1,4)));
        when(roomRepository.findById(2)).thenReturn(Optional.of(buildRoom(2,8)));
        verifyGetAvailableRooms("2018-06-19", "08:00", "09:00", Arrays.asList(1,2));
        verifyGetAvailableRooms("2018-06-19", "09:30", "10:30", Collections.emptyList());
        verifyGetAvailableRooms("2018-06-19", "12:00", "13:00", Arrays.asList(1));
        verifyGetAvailableRooms("2018-06-19", "12:00", "14:00", Collections.emptyList());
        verifyGetAvailableRooms("2018-06-19", "18:00", "19:00", Arrays.asList(1,2));
        verifyGetAvailableRooms("2018-06-19", "22:00", "23:00", Collections.emptyList());
    }

    @Test
    public void testAvailableIntervals() {
        Booking booking1 = buildBooking(1,"2018-06-19", "10:00", "12:00");
        Booking booking2 = buildBooking(1,"2018-06-19", "13:00", "14:00");
        Booking booking3 = buildBooking(1,"2018-06-19", "18:00", "19:00");
        Booking booking4 = buildBooking(1,"2018-06-19", "14:00", "16:00");
        when(bookingRepository.findByBookingDateAndActive(anyString(), anyBoolean())).thenReturn(Arrays.asList(booking1, booking2, booking3, booking4));
        bookingService.getAvailableIntervals(1, "2018-06-19");
    }


    private void verifyGetAvailableRooms (String bookingDate, String fromTime, String toTime, List<Integer> expectedResult) {
        assertThat(bookingService.getAvailableRooms(bookingDate,1, fromTime, toTime)
                .stream()
                .map(RoomView::getId)
                .collect(Collectors.toList()))
                .isEqualTo(expectedResult);

    }*/
}
