package com.devakt.mapper;

import com.devakt.entity.Booking;
import com.devakt.modal.BookingView;
import com.devakt.utils.DateUtils;
import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {}, imports = {DateUtils.class, Date.class})
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(expression = "java(DateUtils.dateToInterval(booking.getFromTime()))", target = "fromTime")
    @Mapping(expression = "java(DateUtils.dateToInterval(booking.getToTime()))", target = "toTime")
    @Mapping(source = "booking.user.email", target = "email")
    @Mapping(source = "booking.room.id", target = "roomId")
    @Mapping(source = "booking.room.name", target = "roomName")
    @Mapping(expression = "java(booking.getId().toString())", target = "bookingId")
    BookingView bookingToBookingView(Booking booking);

    @Mapping(expression = "java(DateUtils.intervalToDate(bookingView.getFromTime()))", target = "fromTime")
    @Mapping(expression = "java(DateUtils.intervalToDate(bookingView.getToTime()))", target = "toTime")
    @Mapping(expression = "java(new Date())", target = "insertDate")
    Booking bookingViewToBooking(BookingView bookingView);

}
