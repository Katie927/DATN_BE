package com.DATN.Bej.mapper;

import com.DATN.Bej.dto.request.identityRequest.PermissionRequest;
import com.DATN.Bej.dto.response.identity.PermissionResponse;
import com.DATN.Bej.entity.identity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
