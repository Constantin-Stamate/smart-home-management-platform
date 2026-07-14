package com.smarthome.management.platform.patterns.structural.composite;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class DeviceLeaf implements SmartHomeComponent {

    private final Device device;

    @Override
    public String name() {
        return device.getName();
    }

    @Override
    public BigDecimal totalEnergy() {
        return device.getEnergyUsage();
    }

    @Override
    public long activeDevices() {
        return device.getStatus() == DeviceStatus.ON ? 1 : 0;
    }
}