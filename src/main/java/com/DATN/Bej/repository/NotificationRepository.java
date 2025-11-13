package com.DATN.Bej.repository;

import org.springframework.stereotype.Repository;
import com.DATN.Bej.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> { 
    List<Notification> findByRecipient_IdOrderByCreatedAtDesc(String userId);
    long countByRecipient_IdAndIsReadFalse(String userId);
}
