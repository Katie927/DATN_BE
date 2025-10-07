package com.DATN.Bej.controller.identity;

import com.DATN.Bej.dto.request.ApiResponse;
import com.DATN.Bej.dto.request.identityRequest.UserUpdateRequest;
import com.DATN.Bej.dto.response.UserResponse;
import com.DATN.Bej.service.identity.UserManageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users/manage")
@Slf4j
public class ManageEmployeeController {

    UserManageService userManageService;

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable String userId){
        return ApiResponse.<UserResponse>builder()
                .result(userManageService.getUser(userId))
                .build();
    }

    @PutMapping("/update/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
//        System.out.println("Request Data: " + request);
        return ApiResponse.<UserResponse>builder()
                .result(userManageService.updateUser(userId, request))
                .build();
    }

}
