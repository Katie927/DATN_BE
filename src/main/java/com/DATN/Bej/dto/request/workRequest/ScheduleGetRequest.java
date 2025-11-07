package com.DATN.Bej.dto.request.workRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleGetRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startOfMonth;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endOfMonth;

}
