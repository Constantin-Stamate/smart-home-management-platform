package com.smarthome.management.platform.patterns.behavioral.state;

import com.smarthome.management.platform.model.enums.DeviceStatus;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class DeviceStateContext {

    private final Map<DeviceStatus, DeviceState> states = new EnumMap<>(DeviceStatus.class);

    public DeviceStateContext(List<DeviceState> stateList) {
        stateList.forEach(state -> states.put(state.status(), state));
    }

    public DeviceState stateFor(DeviceStatus status) {
        return states.get(status);
    }
}