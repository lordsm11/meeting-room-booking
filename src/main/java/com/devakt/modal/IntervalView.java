package com.devakt.modal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class IntervalView {
    String bookingDate;
    String fromTime;
    int fromTimeInt;
    String toTime;
    int toTimeInt;
}
