package com.DATN.Bej.dto.request.identityRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String fullName;
    String password;
    String address;
    LocalDate dob;
    String phoneNumber;
    List<String> roles;
}