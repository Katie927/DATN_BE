package com.DATN.Bej.mapper.workSchedule;

import com.DATN.Bej.dto.request.workRequest.ShiftRequest;
import com.DATN.Bej.dto.response.workResponse.ShiftResponse;
import com.DATN.Bej.entity.work.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShiftMapper {

    Shift toShift(ShiftRequest request);
    @Mapping(source = "id", target = "id")
    ShiftResponse toShiftResponse(Shift shift);

}
