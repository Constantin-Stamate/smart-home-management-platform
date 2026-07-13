package com.smarthome.management.platform.patterns.creational.builder;

import com.smarthome.management.platform.model.enums.DeviceTier;
import com.smarthome.management.platform.model.enums.DeviceType;

import java.math.BigDecimal;

public class DeviceConfigurationBuilder {

    private String name;
    private DeviceType type;
    private DeviceTier tier = DeviceTier.STANDARD;
    private String mode = "Eco";
    private String location = "Interior";
    private BigDecimal energyUsage = BigDecimal.ZERO;
    private BigDecimal temperature = BigDecimal.valueOf(22);

    public DeviceConfigurationBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DeviceConfigurationBuilder type(DeviceType type) {
        this.type = type;
        return this;
    }

    public DeviceConfigurationBuilder tier(DeviceTier tier) {
        this.tier = tier;
        return this;
    }

    public DeviceConfigurationBuilder mode(String mode) {
        if (mode != null && !mode.isBlank()) {
            this.mode = mode;
        }
        return this;
    }

    public DeviceConfigurationBuilder location(String location) {
        if (location != null && !location.isBlank()) {
            this.location = location;
        }
        return this;
    }

    public DeviceConfigurationBuilder energyUsage(BigDecimal energyUsage) {
        this.energyUsage = energyUsage;
        return this;
    }

    public DeviceConfigurationBuilder temperature(BigDecimal temperature) {
        this.temperature = temperature;
        return this;
    }

    public DeviceConfiguration build() {
        return new DeviceConfiguration(name, type, tier, mode, location, energyUsage, temperature);
    }
}