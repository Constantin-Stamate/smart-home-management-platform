package com.smarthome.management.platform.patterns.behavioral.command;

import com.smarthome.management.platform.model.entity.Device;

public class ChangeModeCommand implements DeviceCommand {

    private final Device device;
    private final String mode;
    private String previousMode;

    public ChangeModeCommand(Device device, String mode) {
        this.device = device;
        this.mode = mode;
    }

    @Override
    public Device execute() {
        this.previousMode = device.getMode();
        device.setMode(mode);
        return device;
    }

    @Override
    public void undo() {
        device.setMode(previousMode);
    }
}