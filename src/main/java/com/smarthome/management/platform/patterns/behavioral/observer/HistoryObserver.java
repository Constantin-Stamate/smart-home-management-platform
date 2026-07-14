package com.smarthome.management.platform.patterns.behavioral.observer;

import com.smarthome.management.platform.model.enums.ActivityType;
import com.smarthome.management.platform.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryObserver implements DeviceObserver {

    private final HistoryService historyService;

    @Override
    public void update(DeviceEvent event) {
        historyService.record(event.device(), ActivityType.UPDATED, event.oldStatus(), event.newStatus(), event.message());
    }
}