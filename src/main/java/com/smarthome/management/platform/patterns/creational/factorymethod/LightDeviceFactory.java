package com.smarthome.management.platform.patterns.creational.factorymethod;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceType;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfiguration;
import org.springframework.stereotype.Component;

@Component
public class LightDeviceFactory extends SmartDeviceFactory {

    @Override
    protected Device createDevice(DeviceConfiguration configuration) {
        Device device = new Device();
        device.setMode(configuration.mode() == null ? "Warm" : configuration.mode());
        return device;
    }

    @Override
    public DeviceType supports() {
        return DeviceType.LIGHT;
    }
}