package com.DATN.Bej.event; // (Tạo package 'event' nếu chưa có)

import com.DATN.Bej.dto.ApiNotificationRequest;

/**
 * Một sự kiện chung (generic) được bắn ra
 * khi bất kỳ service nào muốn gửi một thông báo cá nhân.
 */
public record NotificationSendEvent(
    String userId, 
    ApiNotificationRequest request 
) {}