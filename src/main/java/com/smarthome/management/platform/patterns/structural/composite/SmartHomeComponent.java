package com.smarthome.management.platform.patterns.structural.composite;

import java.math.BigDecimal;

public interface SmartHomeComponent {

    String name();

    BigDecimal totalEnergy();

    long activeDevices();
}