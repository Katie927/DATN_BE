package com.DATN.Bej.service;

import com.DATN.Bej.dto.ApiNotificationRequest;
import com.DATN.Bej.dto.NotificationPayload;
import com.DATN.Bej.entity.Notification;
import com.DATN.Bej.entity.identity.User;
import com.DATN.Bej.enums.NotificationType;
import com.DATN.Bej.repository.NotificationRepository;
import com.DATN.Bej.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    private static final String USER_QUEUE = "/queue/notifications";


    @Transactional
    public void createAndSendPersonalNotification(String userId, ApiNotificationRequest request) {

        User recipient = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Notification notification = Notification.builder()
                .recipient(recipient)
                .type(request.type())
                .title(request.title())
                .body(request.body())
                .isRead(false)
                .resourceId(request.metadata() != null ? request.metadata().values().stream().findFirst().orElse(null) : null)
                .build();
        notificationRepository.save(notification); 

        
        NotificationPayload payload = new NotificationPayload(
            UUID.randomUUID(),
            request.type(),
            request.title(),
            request.body(),
            Instant.now(),
            request.metadata()
        );
        messagingTemplate.convertAndSendToUser(userId, USER_QUEUE, payload);
    }

    public void sendBroadcast(ApiNotificationRequest request) {
        NotificationPayload payload = new NotificationPayload(
            UUID.randomUUID(),
            request.type(),
            request.title(),
            request.body(),
            Instant.now(),
            request.metadata()
        );
        messagingTemplate.convertAndSend("/topic/notifications", payload);
    }
    

    public List<Notification> getHistoryForUser(String userId) {
        return notificationRepository.findByRecipient_IdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public boolean markAsRead(String notificationId, String userId) {
        Optional<Notification> notifOpt = notificationRepository.findById(notificationId);
        
        if (notifOpt.isEmpty()) return false;
        
        Notification notification = notifOpt.get();
        
        if (!notification.getRecipient().getId().equals(userId)) {
            throw new SecurityException("User does not have permission to read this notification");
        }
        
        notification.setRead(true);
        notificationRepository.save(notification);
        return true;
    }
}