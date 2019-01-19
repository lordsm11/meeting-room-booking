package com.devakt.controller;

import com.devakt.modal.BookingView;
import com.devakt.service.BookingService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://devakt.ddns.net","http://devakt.ddns.net:3000"}, maxAge = 3600)
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping(value = "/rooms")
    @ResponseBody
    public ResponseEntity rooms() {
        return ResponseEntity.ok().body(bookingService.getRooms());
    }

    @GetMapping(value = "/rooms/{roomId}")
    @ResponseBody
    public ResponseEntity room(@PathVariable int roomId) {
        return ResponseEntity.ok().body(bookingService.getRoom(roomId));
    }

    @GetMapping(value = "/bookings/users/{email}")
    @ResponseBody
    public ResponseEntity getBookings(@PathVariable String email) {
        return ResponseEntity.ok().body(bookingService.getBookings(email));
    }

    @GetMapping(value = "/bookings/rooms/{roomId}")
    @ResponseBody
    public ResponseEntity getRoomBookings(@PathVariable int roomId) {
        return ResponseEntity.ok().body(bookingService.getRoomBookings(roomId));
    }

    @DeleteMapping(value = "/bookings/{bookingId}")
    @ResponseBody
    public ResponseEntity removeBooking(@PathVariable ObjectId bookingId) {
        bookingService.removeBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/config/intervals")
    @ResponseBody
    public ResponseEntity getIntervals() {
        return ResponseEntity.ok().body(bookingService.getIntervals());
    }

    @GetMapping(value = "/rooms/available")
    @ResponseBody
    public ResponseEntity availableRooms(@RequestParam String bookingDate,
                                         @RequestParam int nbPersons,
                                         @RequestParam String fromTime,
                                         @RequestParam String toTime) {
        return ResponseEntity.ok().body(bookingService.getAvailableRooms(bookingDate, nbPersons, fromTime, toTime));
    }

    @GetMapping(value = "/rooms/{roomId}/availableIntervals")
    @ResponseBody
    public ResponseEntity availableIntervals(@PathVariable int roomId, @RequestParam String bookingDate) {
        return ResponseEntity.ok().body(bookingService.getAvailableIntervals(roomId, bookingDate));
    }

    @PostMapping(value = "/book")
    @ResponseBody
    public ResponseEntity book(@RequestBody BookingView bookingView) {
        bookingService.book(bookingView);
        return ResponseEntity.noContent().build();
    }

}
