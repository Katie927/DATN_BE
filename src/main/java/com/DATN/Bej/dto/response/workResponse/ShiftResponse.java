package com.DATN.Bej.dto.response.workResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShiftResponse {

    Long id;
    String name;           // "Ca sáng", "Ca chiều", "Ca tối"
    LocalTime startTime;
    LocalTime endTime;

}
