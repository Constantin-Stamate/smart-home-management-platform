package com.smarthome.management.platform.patterns.behavioral.command;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.patterns.behavioral.state.DeviceStateContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TurnOffCommand implements DeviceCommand {

    private final Device device;
    private final DeviceStateContext stateContext;

    @Override
    public Device execute() {
        stateContext.stateFor(device.getStatus()).turnOff(device);
        return device;
    }

    @Override
    public void undo() {
        stateContext.stateFor(device.getStatus()).turnOn(device);
    }
}