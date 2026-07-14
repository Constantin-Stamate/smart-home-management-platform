package com.smarthome.management.platform.service;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.entity.EnergyConsumption;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.patterns.behavioral.strategy.EnergyStrategyResolver;
import com.smarthome.management.platform.repository.EnergyConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EnergyService {

    private static final BigDecimal PRICE_PER_KWH = BigDecimal.valueOf(2.45);

    private final EnergyConsumptionRepository energyRepository;
    private final EnergyStrategyResolver energyStrategyResolver;

    @Transactional
    public void record(Device device) {
        BigDecimal kwh = energyStrategyResolver.calculate(device);
        EnergyConsumption consumption = new EnergyConsumption();
        consumption.setDevice(device);
        consumption.setKilowattHours(kwh);
        consumption.setEstimatedCost(kwh.multiply(PRICE_PER_KWH));
        energyRepository.save(consumption);
        device.setEnergyUsage(kwh);
    }

    @Transactional(readOnly = true)
    public BigDecimal totalFor(User owner) {
        return energyRepository.sumKilowattHoursByOwner(owner);
    }
}