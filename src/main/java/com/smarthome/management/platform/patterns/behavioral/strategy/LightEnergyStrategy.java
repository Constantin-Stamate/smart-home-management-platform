package com.smarthome.management.platform.patterns.behavioral.strategy;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import com.smarthome.management.platform.model.enums.DeviceType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LightEnergyStrategy implements EnergyConsumptionStrategy {

    @Override
    public boolean supports(DeviceType type) {
        return type == DeviceType.LIGHT;
    }

    @Override
    public BigDecimal calculate(Device device) {
        return device.getStatus() == DeviceStatus.ON ? BigDecimal.valueOf(0.06) : BigDecimal.ZERO;
    }
}