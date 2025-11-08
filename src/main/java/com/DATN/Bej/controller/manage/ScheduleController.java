package com.DATN.Bej.controller.manage;

import com.DATN.Bej.dto.request.ApiResponse;
import com.DATN.Bej.dto.request.workRequest.ScheduleAddRequest;
import com.DATN.Bej.dto.request.workRequest.ScheduleGetRequest;
import com.DATN.Bej.dto.request.workRequest.ShiftRequest;
import com.DATN.Bej.dto.response.workResponse.ScheduleResponse;
import com.DATN.Bej.dto.response.workResponse.ShiftResponse;
import com.DATN.Bej.service.work.ScheduleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/manage/schedule")
public class ScheduleController {

    ScheduleService scheduleService;

    @PostMapping("/monthly")
    ApiResponse<List<ScheduleResponse>> getScheduleMonthly(@RequestBody ScheduleGetRequest request){
        return ApiResponse.<List<ScheduleResponse>>builder()
                .result(scheduleService.getScheduleMonthly(request))
                .build();
    }

    @PostMapping
    ApiResponse<ScheduleResponse> addSchedule(@RequestBody ScheduleAddRequest request){
        return ApiResponse.<ScheduleResponse>builder()
                .result(scheduleService.addWorkSchedule(request))
                .build();
    }

    @PostMapping("/shift")
    ApiResponse<ShiftResponse> addWorkShift(@RequestBody ShiftRequest request){
        return ApiResponse.<ShiftResponse>builder()
                .result(scheduleService.addWorkShift(request))
                .build();
    }

    @GetMapping("/shifts")
    ApiResponse<List<ShiftResponse>> getWorkShift(){
        return ApiResponse.<List<ShiftResponse>>builder()
                .result(scheduleService.getWorkShift())
                .build();
    }

}
