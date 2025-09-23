package com.DATN.Bej.mapper;

import com.DATN.Bej.dto.request.identityRequest.RoleRequest;
import com.DATN.Bej.dto.response.identity.RoleResponse;
import com.DATN.Bej.entity.identity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRResponse(Role permission);
}


