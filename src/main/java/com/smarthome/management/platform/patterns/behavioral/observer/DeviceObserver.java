package com.smarthome.management.platform.patterns.behavioral.observer;

public interface DeviceObserver {

    void update(DeviceEvent event);
}