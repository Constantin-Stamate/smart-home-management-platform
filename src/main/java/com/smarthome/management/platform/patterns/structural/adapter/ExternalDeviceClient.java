package com.smarthome.management.platform.patterns.structural.adapter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExternalDeviceClient {

    public List<ExternalDevicePayload> fetchDemoDevices() {
        return List.of(
                new ExternalDevicePayload("EXT-L1", "Lumina externa hol", "light", "Hol", 9.5),
                new ExternalDevicePayload("EXT-S1", "Senzor miscare garaj", "sensor", "Garaj", 3.0)
        );
    }
}