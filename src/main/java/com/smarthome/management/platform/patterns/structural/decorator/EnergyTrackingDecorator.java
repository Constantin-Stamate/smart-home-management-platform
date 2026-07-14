package com.smarthome.management.platform.patterns.structural.decorator;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;

import java.math.BigDecimal;

public class EnergyTrackingDecorator extends DeviceOperationDecorator {

    public EnergyTrackingDecorator(DeviceOperation delegate) {
        super(delegate);
    }

    @Override
    public Device execute(Device device) {
        Device updated = super.execute(device);
        if (updated.getStatus() == DeviceStatus.ON && updated.getEnergyUsage().compareTo(BigDecimal.ZERO) == 0) {
            updated.setEnergyUsage(BigDecimal.valueOf(0.5));
        }

        return updated;
    }
}