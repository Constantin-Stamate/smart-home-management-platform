package com.smarthome.management.platform.repository;

import com.smarthome.management.platform.model.entity.DeviceHistory;
import com.smarthome.management.platform.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceHistoryRepository extends JpaRepository<DeviceHistory, Long> {

    @EntityGraph(attributePaths = {"device"})
    List<DeviceHistory> findTop12ByDeviceRoomOwnerOrderByCreatedAtDesc(User owner);

    List<DeviceHistory> findByDeviceIdOrderByCreatedAtDesc(Long deviceId);
}