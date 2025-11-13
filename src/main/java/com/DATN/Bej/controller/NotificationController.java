package com.DATN.Bej.controller;

import com.DATN.Bej.dto.ApiNotificationRequest;
import com.DATN.Bej.dto.request.ApiResponse;
import com.DATN.Bej.entity.Notification;
import com.DATN.Bej.service.NotificationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor 
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) 
public class NotificationController {

    NotificationService notificationService;

    /**
     * API gửi cá nhân (dùng bởi admin/service khác)
     * Trả về ApiResponse (giống /log-in)
     */
    @PostMapping("/user/{userId}")
    public ApiResponse<Void> sendToUserById(
            @PathVariable String userId,
            @RequestBody ApiNotificationRequest request) {
        
        // Service sẽ ném RuntimeException (ví dụ: UserNotFound)
        // và sẽ được xử lý bởi một @RestControllerAdvice (giống như logic /log-in)
        notificationService.createAndSendPersonalNotification(userId, request);
        
        return ApiResponse.<Void>builder()
                .message("Đã gửi tin nhắn CÁ NHÂN cho user ID: " + userId)
                .build(); // Mặc định code = 1000 (thành công)
    }

    /**
     * API gửi broadcast (dùng bởi admin/service khác)
     * Trả về ApiResponse (giống /log-in)
     */
    @PostMapping("/broadcast")
    public ApiResponse<Void> sendBroadcastNotification(@RequestBody ApiNotificationRequest request) {
        notificationService.sendBroadcast(request);
        return ApiResponse.<Void>builder()
                .message("Đã gửi broadcast: " + request.title())
                .build();
    }

    /**
     * API lấy LỊCH SỬ (dùng bởi client đã đăng nhập)
     * Trả về ResponseEntity (giống /logout) vì cần check Principal
     */
    @GetMapping("/my-history")
    public ResponseEntity<ApiResponse<List<Notification>>> getMyNotifications(Principal principal) {
        
        // Giống logic check header trong /logout
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponse.<List<Notification>>builder()
                        .code(1001) // Giả sử 1001 là lỗi "Chưa xác thực"
                        .message("User not authenticated")
                        .build()
            );
        }
        
        String userId = principal.getName();
        List<Notification> history = notificationService.getHistoryForUser(userId);
        
        // Trả về 200 OK với kết quả
        return ResponseEntity.ok(
            ApiResponse.<List<Notification>>builder()
                    .result(history)
                    .build()
        );
    }

    /**
     * API đánh dấu ĐÃ ĐỌC (dùng bởi client đã đăng nhập)
     * Trả về ResponseEntity (giống /logout) vì có nhiều logic fail
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable String notificationId,
            Principal principal) {
                
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponse.<Void>builder()
                        .code(1001) 
                        .message("User not authenticated")
                        .build()
            );
        }
        
        try {
            String userId = principal.getName();
            boolean success = notificationService.markAsRead(notificationId, userId);
            
            if (success) {
                // 200 OK
                return ResponseEntity.ok(
                    ApiResponse.<Void>builder()
                            .message("Notification marked as read")
                            .build()
                );
            } else {
                // 404 Not Found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.<Void>builder()
                            .code(1004) // Giả sử 1004 là "Not Found"
                            .message("Notification not found")
                            .build()
                );
            }
        } catch (SecurityException e) {
            // 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ApiResponse.<Void>builder()
                        .code(1003) // Giả sử 1003 là "Không có quyền"
                        .message(e.getMessage())
                        .build()
            );
        }
    }
}