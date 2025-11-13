package com.DATN.Bej.dto;

import com.DATN.Bej.enums.NotificationType;
import java.util.Map;

public record ApiNotificationRequest(
    NotificationType type,
    String title,
    String body,
    Map<String, String> metadata
) {}