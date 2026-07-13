package com.smarthome.management.platform.patterns.creational.abstractfactory;

import com.smarthome.management.platform.model.enums.DeviceTier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceFamilyFactoryProvider {

    private final StandardDeviceFamilyFactory standardFactory;
    private final PremiumDeviceFamilyFactory premiumFactory;

    public DeviceFamilyFactory forTier(DeviceTier tier) {
        return tier == DeviceTier.PREMIUM ? premiumFactory : standardFactory;
    }
}