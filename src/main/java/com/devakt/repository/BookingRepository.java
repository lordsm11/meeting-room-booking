package com.devakt.repository;

import com.devakt.entity.Booking;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, ObjectId> {

    List<Booking> findByBookingDateAndActive(String bookingDate, boolean active);

    List<Booking> findByBookingDateAndRoomIdAndActive(String bookingDate, int roomId, boolean active);

    List<Booking> findByRoomIdAndActive(int roomId, boolean active);

    List<Booking> findByUserId(Integer userId);
}