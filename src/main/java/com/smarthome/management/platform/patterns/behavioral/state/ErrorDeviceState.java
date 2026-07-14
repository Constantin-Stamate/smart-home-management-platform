package com.smarthome.management.platform.patterns.behavioral.state;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorDeviceState implements DeviceState {

    @Override
    public DeviceStatus status() {
        return DeviceStatus.ERROR;
    }

    @Override
    public void turnOn(Device device) {
        throw new IllegalStateException("Dispozitivul este in eroare si nu poate fi pornit.");
    }

    @Override
    public void turnOff(Device device) {
        device.setStatus(DeviceStatus.OFF);
    }

    @Override
    public void standby(Device device) {
        throw new IllegalStateException("Dispozitivul este in eroare si nu poate intra in standby.");
    }
}