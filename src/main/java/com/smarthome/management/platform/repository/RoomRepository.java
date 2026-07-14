package com.smarthome.management.platform.repository;

import com.smarthome.management.platform.model.entity.Room;
import com.smarthome.management.platform.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @EntityGraph(attributePaths = {"devices"})
    List<Room> findByOwnerOrderByNameAsc(User owner);

    @EntityGraph(attributePaths = {"devices"})
    Optional<Room> findByIdAndOwner(Long id, User owner);

    long countByOwner(User owner);
}