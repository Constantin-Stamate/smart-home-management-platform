package com.smarthome.management.platform.patterns.structural.decorator;

import com.smarthome.management.platform.model.entity.Device;

public interface DeviceOperation {

    Device execute(Device device);
}