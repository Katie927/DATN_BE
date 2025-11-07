package com.DATN.Bej.service.work;

import com.DATN.Bej.dto.request.workRequest.ScheduleAddRequest;
import com.DATN.Bej.dto.request.workRequest.ScheduleGetRequest;
import com.DATN.Bej.dto.request.workRequest.ShiftRequest;
import com.DATN.Bej.dto.response.workResponse.ScheduleResponse;
import com.DATN.Bej.dto.response.workResponse.ShiftResponse;
import com.DATN.Bej.entity.identity.User;
import com.DATN.Bej.entity.work.Shift;
import com.DATN.Bej.entity.work.WorkSchedule;
import com.DATN.Bej.exception.AppException;
import com.DATN.Bej.exception.ErrorCode;
import com.DATN.Bej.mapper.workSchedule.ScheduleMapper;
import com.DATN.Bej.mapper.workSchedule.ShiftMapper;
import com.DATN.Bej.repository.UserRepository;
import com.DATN.Bej.repository.workSchedule.ScheduleRepository;
import com.DATN.Bej.repository.workSchedule.ShiftRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleService {

    ShiftMapper shiftMapper;
    ShiftRepository shiftRepository;
    ScheduleMapper scheduleMapper;
    ScheduleRepository scheduleRepository;
    UserRepository userRepository;

    public List<ScheduleResponse> getScheduleMonthly(ScheduleGetRequest request){
        return scheduleRepository
                .findByDateRange(request.getStartOfMonth(), request.getEndOfMonth())
                .stream().map(scheduleMapper::toScheduleResponse).toList();
    }

    public ScheduleResponse addWorkSchedule(ScheduleAddRequest request){
        log.info("Add Schedule");
        log.info(request.getUserId().getFirst());
        WorkSchedule schedule = scheduleMapper.toSchedule(request);
        Set<User> users = new HashSet<>(userRepository.findAllById(request.getUserId()));
        schedule.setUsers(users);
        Shift shift = shiftRepository.findById(request.getShiftId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        schedule.setShift(shift);
//        log.info(schedule.getUser().getFullName());
        log.info(schedule.getShift().getName());
        return scheduleMapper.toScheduleResponse(scheduleRepository.save(schedule));
    }

    public ShiftResponse addWorkShift(ShiftRequest request){
//        log.info(String.valueOf(request.getStartTime()));
        Shift shift = shiftMapper.toShift(request);
        return shiftMapper.toShiftResponse(shiftRepository.save(shift));
    }

    public List<ShiftResponse> getWorkShift(){
        return shiftRepository.findAll().stream().map(shiftMapper::toShiftResponse).toList();
    }

}
