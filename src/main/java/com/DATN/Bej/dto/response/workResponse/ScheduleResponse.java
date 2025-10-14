package com.DATN.Bej.dto.response.workResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleResponse {

    String shiftName;
    String userName;
    LocalDate workDate;

}

