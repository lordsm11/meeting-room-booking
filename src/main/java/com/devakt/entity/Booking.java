package com.devakt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Booking implements Serializable {
    @Id
    ObjectId id;

    @DBRef
    User user;

    @DBRef
    Room room;

    String description;
    String bookingDate;
    String fromTime;
    String toTime;
    int nbPersons;
    Date insertDate;

    boolean active;
    Date removeDate;
}



