package com.smarthome.management.platform.service;

import com.smarthome.management.platform.dto.RoomRequest;
import com.smarthome.management.platform.model.entity.Room;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<Room> findFor(User owner) {
        return roomRepository.findByOwnerOrderByNameAsc(owner);
    }

    @Transactional(readOnly = true)
    public Room getOwned(Long roomId, User owner) {
        return roomRepository.findByIdAndOwner(roomId, owner)
                .orElseThrow(() -> new IllegalArgumentException("Camera nu exista."));
    }

    @Transactional
    public Room create(RoomRequest request, User owner) {
        Room room = new Room();
        room.setOwner(owner);
        room.setName(request.getName());
        room.setFloor(request.getFloor());
        room.setDescription(request.getDescription());
        return roomRepository.save(room);
    }

    @Transactional
    public void update(Long id, RoomRequest request, User owner) {
        Room room = getOwned(id, owner);
        room.setName(request.getName());
        room.setFloor(request.getFloor());
        room.setDescription(request.getDescription());
        roomRepository.save(room);
    }

    @Transactional
    public void delete(Long id, User owner) {
        Room room = getOwned(id, owner);
        roomRepository.delete(room);
    }
}