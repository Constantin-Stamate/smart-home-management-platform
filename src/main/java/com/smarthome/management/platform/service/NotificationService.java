package com.smarthome.management.platform.service;

import com.smarthome.management.platform.model.entity.Notification;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void notify(User user, String title, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<Notification> recent(User user) {
        return notificationRepository.findTop8ByUserOrderByCreatedAtDesc(user);
    }
}