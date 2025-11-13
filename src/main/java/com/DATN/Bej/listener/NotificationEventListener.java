package com.DATN.Bej.listener;

import com.DATN.Bej.event.NotificationSendEvent;
import com.DATN.Bej.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/* 
```
cach su dung notificationEvent de day notificaion la tao mot ApiNotificationRequest sau do ban su kien ve cho ApplicationEventPublisher
Vi du:
private final ApplicationEventPublisher eventPublisher;

ApiNotificationRequest notifRequest = new ApiNotificationRequest(
            NotificationType.ORDER_PLACED,
            "Đơn hàng mới!",
            "Bạn vừa đặt thành công đơn hàng #" + savedOrder.getId(),
            Map.of("orderId", savedOrder.getId())
        );
        

        eventPublisher.publishEvent(new NotificationSendEvent(userId, notifRequest));
        
```
*/

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    /**
     * Phương thức này sẽ tự động được gọi khi có một NotificationSendEvent
     * được bắn ra (publish) từ bất kỳ đâu trong ứng dụng.
     * * @Async: Đảm bảo nó chạy trên một thread riêng (bất đồng bộ)
     * mà không chặn thread chính (ví dụ: thread đặt hàng).
     */
    @Async
    @EventListener
    public void handleNotificationSendEvent(NotificationSendEvent event) {
        log.info("Bắt được sự kiện gửi thông báo cho user: {}", event.userId());
        try {
            // Lấy thông tin từ sự kiện và gọi service
            notificationService.createAndSendPersonalNotification(
                event.userId(),
                event.request()
            );
            log.info("Đã gửi thông báo thành công cho user: {}", event.userId());
        } catch (Exception e) {

            log.error(
                "LỖI Bất Đồng Bộ: Không thể gửi thông báo cho user: " + event.userId(), 
                e
            );
        }
    }
}