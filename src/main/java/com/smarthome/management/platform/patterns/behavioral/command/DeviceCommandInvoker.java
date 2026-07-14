package com.smarthome.management.platform.patterns.behavioral.command;

import com.smarthome.management.platform.model.entity.Device;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class DeviceCommandInvoker {

    private final Deque<DeviceCommand> history = new ArrayDeque<>();

    public Device execute(DeviceCommand command) {
        Device result = command.execute();
        history.push(command);
        return result;
    }

    public void undo() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }

    public boolean canUndo() {
        return !history.isEmpty();
    }
}