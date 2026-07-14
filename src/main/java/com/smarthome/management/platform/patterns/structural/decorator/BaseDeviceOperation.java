package com.smarthome.management.platform.patterns.structural.decorator;

import com.smarthome.management.platform.model.entity.Device;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class BaseDeviceOperation implements DeviceOperation {

    private final Consumer<Device> action;

    @Override
    public Device execute(Device device) {
        action.accept(device);
        return device;
    }
}