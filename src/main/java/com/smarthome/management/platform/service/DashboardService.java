package com.smarthome.management.platform.service;

import com.smarthome.management.platform.dto.DashboardView;
import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.entity.Room;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import com.smarthome.management.platform.patterns.structural.composite.DeviceLeaf;
import com.smarthome.management.platform.patterns.structural.composite.HomeComposite;
import com.smarthome.management.platform.patterns.structural.composite.RoomComposite;
import com.smarthome.management.platform.repository.DeviceRepository;
import com.smarthome.management.platform.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;
    private final HistoryService historyService;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public DashboardView dashboardFor(User owner) {
        List<Room> rooms = roomRepository.findByOwnerOrderByNameAsc(owner);
        List<Device> devices = deviceRepository.findByRoomOwnerOrderByNameAsc(owner);
        HomeComposite home = new HomeComposite();
        rooms.forEach(room -> {
            RoomComposite roomComposite = new RoomComposite(room.getName());
            devices.stream().filter(device -> device.getRoom().getId().equals(room.getId())).map(DeviceLeaf::new).forEach(roomComposite::add);
            home.add(roomComposite);
        });

        return new DashboardView(rooms.size(), devices.size(), deviceRepository.countByRoomOwnerAndStatus(owner, DeviceStatus.ON), home.totalEnergy(), rooms, devices, historyService.recent(owner), notificationService.recent(owner));
    }
}