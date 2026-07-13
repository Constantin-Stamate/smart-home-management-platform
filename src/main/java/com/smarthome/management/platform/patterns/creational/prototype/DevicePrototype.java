package com.smarthome.management.platform.patterns.creational.prototype;

import com.smarthome.management.platform.model.entity.Device;

public interface DevicePrototype {

    Device copyFrom(Device source, String newName);
}