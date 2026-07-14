package com.smarthome.management.platform.patterns.structural.decorator;

import com.smarthome.management.platform.model.entity.Device;

import java.time.LocalDateTime;

public class AuditDecorator extends DeviceOperationDecorator {

    public AuditDecorator(DeviceOperation delegate) {
        super(delegate);
    }

    @Override
    public Device execute(Device device) {
        Device updated = super.execute(device);
        updated.setLastUpdatedAt(LocalDateTime.now());
        return updated;
    }
}