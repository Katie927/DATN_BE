package com.DATN.Bej.mapper.workSchedule;

import com.DATN.Bej.dto.request.workRequest.ScheduleAddRequest;
import com.DATN.Bej.dto.response.workResponse.ScheduleResponse;
import com.DATN.Bej.entity.work.WorkSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    WorkSchedule toSchedule(ScheduleAddRequest request);

    @Mapping(target = "userName", source = "user.fullName")
    @Mapping(target = "shiftName", source = "shift.name")
    ScheduleResponse toScheduleResponse(WorkSchedule schedule);

}
