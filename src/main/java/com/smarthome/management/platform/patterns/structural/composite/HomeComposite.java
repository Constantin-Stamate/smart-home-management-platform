package com.smarthome.management.platform.patterns.structural.composite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HomeComposite implements SmartHomeComponent {

    private final List<SmartHomeComponent> rooms = new ArrayList<>();

    public void add(SmartHomeComponent component) {
        rooms.add(component);
    }

    @Override
    public String name() {
        return "Casa inteligenta";
    }

    @Override
    public BigDecimal totalEnergy() {
        return rooms.stream()
                .map(SmartHomeComponent::totalEnergy)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public long activeDevices() {
        return rooms.stream().mapToLong(SmartHomeComponent::activeDevices).sum();
    }
}