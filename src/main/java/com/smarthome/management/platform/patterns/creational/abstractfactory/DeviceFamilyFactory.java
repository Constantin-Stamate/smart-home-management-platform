package com.smarthome.management.platform.patterns.creational.abstractfactory;

import com.smarthome.management.platform.model.enums.DeviceType;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfiguration;

public interface DeviceFamilyFactory {

    DeviceConfiguration defaultConfiguration(String name, DeviceType type, String location);
}