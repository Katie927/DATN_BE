package com.DATN.Bej.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION (9999, "Uncategorized_exception_Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(10001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED (1002, "User existed", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1006, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED (1004, "User not existed", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1003, "Username must be at least 6 characters", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "User unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "User not have permission", HttpStatus.FORBIDDEN),

    ROLE_NOT_FOUND(1011, "Role not found", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
