package com.smarthome.management.platform.patterns.structural.decorator;

import com.smarthome.management.platform.model.entity.Device;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DeviceOperationDecorator implements DeviceOperation {

    protected final DeviceOperation delegate;

    @Override
    public Device execute(Device device) {
        return delegate.execute(device);
    }
}