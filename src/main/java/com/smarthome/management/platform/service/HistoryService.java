package com.smarthome.management.platform.service;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.entity.DeviceHistory;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.model.enums.ActivityType;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import com.smarthome.management.platform.repository.DeviceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final DeviceHistoryRepository historyRepository;

    @Transactional
    public void record(Device device, ActivityType type, DeviceStatus oldStatus, DeviceStatus newStatus, String message) {
        DeviceHistory history = new DeviceHistory();
        history.setDevice(device);
        history.setActivityType(type);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setMessage(message);
        historyRepository.save(history);
    }

    @Transactional(readOnly = true)
    public List<DeviceHistory> recent(User owner) {
        return historyRepository.findTop12ByDeviceRoomOwnerOrderByCreatedAtDesc(owner);
    }
}