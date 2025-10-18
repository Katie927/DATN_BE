package com.DATN.Bej.dto.request.workRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleGetRequest {

    LocalDate startOfMonth;
    LocalDate endOfMonth;

}
