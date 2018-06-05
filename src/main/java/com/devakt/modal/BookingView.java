package com.devakt.modal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookingView {
    String bookingId;
    String description;
    String email;
    int roomId;
    String roomName;
    String bookingDate;
    int fromTime;
    int toTime;
    int nbPersons;
    boolean active;
}
