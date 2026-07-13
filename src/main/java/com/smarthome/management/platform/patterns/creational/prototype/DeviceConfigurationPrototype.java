package com.smarthome.management.platform.patterns.creational.prototype;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import org.springframework.stereotype.Component;

@Component
public class DeviceConfigurationPrototype implements DevicePrototype {

    @Override
    public Device copyFrom(Device source, String newName) {
        Device clone = new Device();
        clone.setName(newName);
        clone.setType(source.getType());
        clone.setTier(source.getTier());
        clone.setMode(source.getMode());
        clone.setLocation(source.getLocation());
        clone.setTemperature(source.getTemperature());
        clone.setEnergyUsage(source.getEnergyUsage());
        clone.setOnline(source.isOnline());
        clone.setStatus(DeviceStatus.OFF);
        return clone;
    }
}