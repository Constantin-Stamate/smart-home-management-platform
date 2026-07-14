package com.smarthome.management.platform.patterns.behavioral.observer;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.enums.DeviceStatus;

public record DeviceEvent(Device device, DeviceStatus oldStatus, DeviceStatus newStatus, String message) {
}