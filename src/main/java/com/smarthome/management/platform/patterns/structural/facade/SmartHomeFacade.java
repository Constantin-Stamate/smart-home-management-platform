package com.smarthome.management.platform.patterns.structural.facade;

import com.smarthome.management.platform.dto.DashboardView;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.service.DashboardService;
import com.smarthome.management.platform.service.DeviceService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmartHomeFacade {

    private final DashboardService dashboardService;
    private final DeviceService deviceService;

    public DashboardView dashboard(User user) {
        return dashboardService.dashboardFor(user);
    }

    public void turnOn(Long deviceId, User user) {
        deviceService.turnOn(deviceId, user);
    }

    public void turnOff(Long deviceId, User user) {
        deviceService.turnOff(deviceId, user);
    }
}