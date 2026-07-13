package com.smarthome.management.platform.patterns.creational.factorymethod;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import com.smarthome.management.platform.model.enums.DeviceType;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfiguration;

public abstract class SmartDeviceFactory {

    public Device create(DeviceConfiguration configuration) {
        Device device = createDevice(configuration);
        device.setName(configuration.name());
        device.setType(configuration.type());
        device.setTier(configuration.tier());
        device.setMode(configuration.mode());
        device.setLocation(configuration.location());
        device.setEnergyUsage(configuration.energyUsage());
        device.setTemperature(configuration.temperature());
        device.setStatus(DeviceStatus.OFF);
        return device;
    }

    protected abstract Device createDevice(DeviceConfiguration configuration);

    public abstract DeviceType supports();
}