package com.smarthome.management.platform.patterns.behavioral.state;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;

public interface DeviceState {

    DeviceStatus status();

    void turnOn(Device device);

    void turnOff(Device device);

    void standby(Device device);
}