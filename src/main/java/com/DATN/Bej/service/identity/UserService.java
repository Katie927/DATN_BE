package com.DATN.Bej.service.identity;

import com.DATN.Bej.dto.request.identityRequest.UserCreationRequest;
import com.DATN.Bej.dto.request.identityRequest.UserUpdateRequest;
import com.DATN.Bej.dto.response.UserResponse;
import com.DATN.Bej.entity.identity.Role;
import com.DATN.Bej.entity.identity.User;
import com.DATN.Bej.exception.AppException;
import com.DATN.Bej.exception.ErrorCode;
import com.DATN.Bej.mapper.UserMapper;
import com.DATN.Bej.repository.RoleRepository;
import com.DATN.Bej.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
//    RoleMapper roleMapper;

    // create User
    public UserResponse createUser(UserCreationRequest request){

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        System.out.println("request role: " + request.getRole());
        var role = roleRepository.findById(request.getRole())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));;
        System.out.println("role: " + role);
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

//        var roles = roleRepository.findAllById(request.getRoles());
//        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateMyInfo(UserUpdateRequest request){
        System.out.println("Update my info");
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));;
        userMapper.updateUser(user, request);
        if(request.getPassword() != null){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByPhoneNumber(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}

