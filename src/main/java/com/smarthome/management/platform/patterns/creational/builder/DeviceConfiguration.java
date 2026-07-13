package com.smarthome.management.platform.patterns.creational.builder;

import com.smarthome.management.platform.model.enums.DeviceTier;
import com.smarthome.management.platform.model.enums.DeviceType;

import java.math.BigDecimal;

public record DeviceConfiguration(

        String name,
        DeviceType type,
        DeviceTier tier,
        String mode,
        String location,
        BigDecimal energyUsage,
        BigDecimal temperature
) {
}