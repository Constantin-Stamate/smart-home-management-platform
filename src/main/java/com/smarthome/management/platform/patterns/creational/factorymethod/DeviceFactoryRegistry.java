package com.smarthome.management.platform.patterns.creational.factorymethod;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceFactoryRegistry {

    private final List<SmartDeviceFactory> factories;
    private final GenericDeviceFactory genericDeviceFactory;

    public Device createDevice(DeviceConfiguration configuration) {
        return factories.stream()
                .filter(factory -> configuration.type().equals(factory.supports()))
                .findFirst()
                .orElse(genericDeviceFactory)
                .create(configuration);
    }
}