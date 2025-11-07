package com.DATN.Bej.dto.request.workRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleAddRequest {

    List<String> userId;
    LocalDate workDate;
    Long shiftId;

}
