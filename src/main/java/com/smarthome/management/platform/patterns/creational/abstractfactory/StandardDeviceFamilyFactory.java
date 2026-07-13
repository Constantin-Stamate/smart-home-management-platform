package com.smarthome.management.platform.patterns.creational.abstractfactory;

import com.smarthome.management.platform.model.enums.DeviceTier;
import com.smarthome.management.platform.model.enums.DeviceType;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfiguration;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfigurationBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StandardDeviceFamilyFactory implements DeviceFamilyFactory {

    @Override
    public DeviceConfiguration defaultConfiguration(String name, DeviceType type, String location) {
        return new DeviceConfigurationBuilder()
                .name(name)
                .type(type)
                .tier(DeviceTier.STANDARD)
                .mode("Eco")
                .location(location)
                .energyUsage(BigDecimal.valueOf(0.8))
                .temperature(BigDecimal.valueOf(22))
                .build();
    }
}