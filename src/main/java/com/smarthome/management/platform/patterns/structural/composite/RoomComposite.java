package com.smarthome.management.platform.patterns.structural.composite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomComposite implements SmartHomeComponent {

    private final String name;
    private final List<SmartHomeComponent> children = new ArrayList<>();

    public RoomComposite(String name) {
        this.name = name;
    }

    public void add(SmartHomeComponent component) {
        children.add(component);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public BigDecimal totalEnergy() {
        return children.stream()
                .map(SmartHomeComponent::totalEnergy)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public long activeDevices() {
        return children.stream().mapToLong(SmartHomeComponent::activeDevices).sum();
    }
}