package com.smarthome.management.platform.patterns.structural.adapter;

public record ExternalDevicePayload(

        String externalId,
        String label,
        String category,
        String roomName,
        double powerWatts
) {
}