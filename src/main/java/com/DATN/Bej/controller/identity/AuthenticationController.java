package com.DATN.Bej.controller.identity;

import com.DATN.Bej.dto.request.ApiResponse;
import com.DATN.Bej.dto.request.identityRequest.AuthenticationRequest;
import com.DATN.Bej.dto.request.identityRequest.IntrospectRequest;
import com.DATN.Bej.dto.response.identity.AuthenticationResponse;
import com.DATN.Bej.dto.response.identity.IntrospectResponse;
import com.DATN.Bej.service.identity.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        System.out.println("Received request: " + request);
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request)
            throws ParseException, JOSEException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<Void>builder()
                            .message("Missing or invalid Authorization header")
//                            .status(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
//        System.out.println("Received request: " + request);
        String token = authHeader.substring(7);
        IntrospectRequest introspectRequest = IntrospectRequest.builder()
                .token(token)
                .build();
        authenticationService.logout(introspectRequest);
//        return ApiResponse.<Void>builder()
//                .build();
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .message("Logged out successfully")
//                        .status(HttpStatus.)
                        .build()
        );
    }

}
