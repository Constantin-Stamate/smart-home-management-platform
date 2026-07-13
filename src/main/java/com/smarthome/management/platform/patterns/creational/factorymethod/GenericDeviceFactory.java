package com.smarthome.management.platform.patterns.creational.factorymethod;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceType;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfiguration;
import org.springframework.stereotype.Component;

@Component
public class GenericDeviceFactory extends SmartDeviceFactory {

    @Override
    protected Device createDevice(DeviceConfiguration configuration) {
        return new Device();
    }

    @Override
    public DeviceType supports() {
        return null;
    }
}