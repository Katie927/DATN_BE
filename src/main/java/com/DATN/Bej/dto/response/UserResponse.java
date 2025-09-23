package com.DATN.Bej.dto.response;

import com.DATN.Bej.dto.response.identity.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String fullName;
    String address;
    LocalDate dob;
    String email;
    String phoneNumber;
    Set<RoleResponse> roles;
}
