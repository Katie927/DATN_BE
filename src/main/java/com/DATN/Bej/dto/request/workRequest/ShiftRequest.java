package com.DATN.Bej.dto.request.workRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShiftRequest {

    private String name;           // "Ca sáng", "Ca chiều", "Ca tối"
    private LocalTime startTime;
    private LocalTime endTime;

}
