package com.smarthome.management.platform.patterns.behavioral.command;

import com.smarthome.management.platform.model.entity.Device;

public interface DeviceCommand {

    Device execute();

    void undo();
}