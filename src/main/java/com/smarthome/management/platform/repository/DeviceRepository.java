package com.smarthome.management.platform.repository;

import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.entity.Room;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @EntityGraph(attributePaths = {"room"})
    List<Device> findByRoomOwnerOrderByNameAsc(User owner);

    List<Device> findByRoom(Room room);

    @EntityGraph(attributePaths = {"room", "room.owner"})
    Optional<Device> findByIdAndRoomOwner(Long id, User owner);

    @Override
    @EntityGraph(attributePaths = {"room", "room.owner"})
    List<Device> findAll();

    long countByRoomOwner(User owner);

    long countByRoomOwnerAndStatus(User owner, DeviceStatus status);
}