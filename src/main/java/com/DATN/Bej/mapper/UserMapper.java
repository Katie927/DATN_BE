package com.DATN.Bej.mapper;

import com.DATN.Bej.dto.request.identityRequest.UserCreationRequest;
import com.DATN.Bej.dto.request.identityRequest.UserUpdateRequest;
import com.DATN.Bej.dto.response.UserResponse;
import com.DATN.Bej.entity.identity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
