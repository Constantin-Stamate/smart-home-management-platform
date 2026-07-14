package com.smarthome.management.platform.patterns.structural.adapter;

import com.smarthome.management.platform.model.entity.Device;

public interface DeviceImportAdapter {

    Device adapt(ExternalDevicePayload payload);
}