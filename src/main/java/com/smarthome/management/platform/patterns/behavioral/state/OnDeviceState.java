package com.smarthome.management.platform.patterns.behavioral.state;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import org.springframework.stereotype.Component;

@Component
public class OnDeviceState implements DeviceState {

    @Override
    public DeviceStatus status() {
        return DeviceStatus.ON;
    }

    @Override
    public void turnOn(Device device) {
        device.setStatus(DeviceStatus.ON);
    }

    @Override
    public void turnOff(Device device) {
        device.setStatus(DeviceStatus.OFF);
    }

    @Override
    public void standby(Device device) {
        device.setStatus(DeviceStatus.STANDBY);
    }
}