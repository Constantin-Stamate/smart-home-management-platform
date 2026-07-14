package com.smarthome.management.platform.repository;

import com.smarthome.management.platform.model.entity.Notification;
import com.smarthome.management.platform.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findTop8ByUserOrderByCreatedAtDesc(User user);
}