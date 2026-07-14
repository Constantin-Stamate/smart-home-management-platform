package com.smarthome.management.platform.patterns.behavioral.strategy;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultEnergyStrategy implements EnergyConsumptionStrategy {

    @Override
    public boolean supports(DeviceType type) {
        return true;
    }

    @Override
    public BigDecimal calculate(Device device) {
        return device.getEnergyUsage().max(BigDecimal.valueOf(0.02));
    }
}