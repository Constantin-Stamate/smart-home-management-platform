package com.smarthome.management.platform.patterns.behavioral.observer;

import com.smarthome.management.platform.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationObserver implements DeviceObserver {

    private final NotificationService notificationService;

    @Override
    public void update(DeviceEvent event) {
        notificationService.notify(
                event.device().getRoom().getOwner(),
                "Schimbare dispozitiv",
                event.message()
        );
    }
}