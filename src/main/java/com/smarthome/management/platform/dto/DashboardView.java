package com.smarthome.management.platform.dto;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.entity.DeviceHistory;
import com.smarthome.management.platform.model.entity.Notification;
import com.smarthome.management.platform.model.entity.Room;

import java.math.BigDecimal;
import java.util.List;

public record DashboardView(

        long roomCount,
        long deviceCount,
        long activeDevices,
        BigDecimal totalEnergy,
        List<Room> rooms,
        List<Device> devices,
        List<DeviceHistory> recentHistory,
        List<Notification> notifications
) {
}