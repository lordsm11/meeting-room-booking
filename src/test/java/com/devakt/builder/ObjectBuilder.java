/*
 * Copyright (c) 2018 Ekino
 */
package com.devakt.builder;

import com.devakt.entity.Booking;
import com.devakt.entity.Room;
import com.devakt.modal.BookingView;
import org.bson.types.ObjectId;

public class ObjectBuilder {

    public static Room buildRoom(int id, int nbPerson) {
        return Room.builder()
                .id(id)
                .description("description"+id)
                .name("name"+id)
                .nbPersons(nbPerson)
                .build();
    }

    public static Booking buildBooking(int roomId, String bookingDate, String fromTime, String toTime) {
        return Booking.builder()
                .id(new ObjectId("fddfdff"))
                .bookingDate(bookingDate)
                .fromTime(fromTime)
                .toTime(toTime)
                .room(Room.builder().id(roomId).build())
                .build();
    }

    public static BookingView buildBookingView(String bookingDate, int fromTime, int toTime, Integer roomId) {
        BookingView.BookingViewBuilder bookingViewBuilder =  BookingView.builder()
                .fromTime(fromTime)
                .toTime(toTime)
                .bookingDate(bookingDate);

        if(roomId != null) {
            bookingViewBuilder.roomId(roomId);
        }

        return bookingViewBuilder.build();
    }

}