package com.smarthome.management.platform.patterns.behavioral.strategy;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceType;

import java.math.BigDecimal;

public interface EnergyConsumptionStrategy {

    boolean supports(DeviceType type);

    BigDecimal calculate(Device device);
}