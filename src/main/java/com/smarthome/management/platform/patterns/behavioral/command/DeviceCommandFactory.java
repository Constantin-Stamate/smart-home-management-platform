package com.smarthome.management.platform.patterns.behavioral.command;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.patterns.behavioral.state.DeviceStateContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceCommandFactory {

    private final DeviceStateContext stateContext;

    public DeviceCommand turnOn(Device device) {
        return new TurnOnCommand(device, stateContext);
    }

    public DeviceCommand turnOff(Device device) {
        return new TurnOffCommand(device, stateContext);
    }

    public DeviceCommand changeMode(Device device, String mode) {
        return new ChangeModeCommand(device, mode);
    }
}