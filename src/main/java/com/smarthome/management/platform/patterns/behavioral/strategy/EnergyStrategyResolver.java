package com.smarthome.management.platform.patterns.behavioral.strategy;

import com.smarthome.management.platform.model.entity.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EnergyStrategyResolver {

    private final List<EnergyConsumptionStrategy> strategies;
    private final DefaultEnergyStrategy defaultStrategy;

    public BigDecimal calculate(Device device) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(device.getType()) && strategy != defaultStrategy)
                .findFirst()
                .orElse(defaultStrategy)
                .calculate(device);
    }
}