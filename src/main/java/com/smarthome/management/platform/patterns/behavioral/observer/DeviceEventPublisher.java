package com.smarthome.management.platform.patterns.behavioral.observer;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceEventPublisher {

    private final List<DeviceObserver> observers;

    public DeviceEventPublisher(List<DeviceObserver> observers) {
        this.observers = observers;
    }

    public void publish(DeviceEvent event) {
        observers.forEach(observer -> observer.update(event));
    }
}