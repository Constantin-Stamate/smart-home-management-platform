package com.smarthome.management.platform.repository;

import com.smarthome.management.platform.model.entity.EnergyConsumption;
import com.smarthome.management.platform.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Long> {

    @Query("select coalesce(sum(e.kilowattHours), 0) from EnergyConsumption e where e.device.room.owner = :owner")
    BigDecimal sumKilowattHoursByOwner(@Param("owner") User owner);
}