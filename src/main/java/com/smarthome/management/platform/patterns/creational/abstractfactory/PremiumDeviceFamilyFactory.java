package com.smarthome.management.platform.patterns.creational.abstractfactory;

import com.smarthome.management.platform.model.enums.DeviceTier;
import com.smarthome.management.platform.model.enums.DeviceType;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfiguration;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfigurationBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PremiumDeviceFamilyFactory implements DeviceFamilyFactory {

    @Override
    public DeviceConfiguration defaultConfiguration(String name, DeviceType type, String location) {
        return new DeviceConfigurationBuilder()
                .name(name)
                .type(type)
                .tier(DeviceTier.PREMIUM)
                .mode("Auto AI")
                .location(location)
                .energyUsage(BigDecimal.valueOf(1.2))
                .temperature(BigDecimal.valueOf(21))
                .build();
    }
}