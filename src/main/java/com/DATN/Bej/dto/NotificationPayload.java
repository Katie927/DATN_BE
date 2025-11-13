package com.DATN.Bej.dto;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import com.DATN.Bej.enums.NotificationType;

public record NotificationPayload (
    UUID messageId,
    NotificationType type,
    String title,
    String body,
    Instant timestamp,
    Map<String, String> metadata
) {}