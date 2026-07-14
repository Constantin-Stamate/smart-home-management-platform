package com.smarthome.management.platform.patterns.structural.adapter;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import com.smarthome.management.platform.model.enums.DeviceTier;
import com.smarthome.management.platform.model.enums.DeviceType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExternalDeviceAdapter implements DeviceImportAdapter {

    @Override
    public Device adapt(ExternalDevicePayload payload) {
        Device device = new Device();
        device.setName(payload.label());
        device.setType(mapType(payload.category()));
        device.setTier(DeviceTier.STANDARD);
        device.setStatus(DeviceStatus.OFF);
        device.setMode("Imported");
        device.setLocation(payload.roomName());
        device.setEnergyUsage(BigDecimal.valueOf(payload.powerWatts()).divide(BigDecimal.valueOf(1000)));
        return device;
    }

    private DeviceType mapType(String category) {
        return switch (category.toLowerCase()) {
            case "light" -> DeviceType.LIGHT;
            case "sensor" -> DeviceType.SENSOR;
            case "camera" -> DeviceType.CAMERA;
            default -> DeviceType.ENERGY_METER;
        };
    }
}