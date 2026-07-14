package com.smarthome.management.platform.config;

import com.smarthome.management.platform.model.entity.*;
import com.smarthome.management.platform.model.enums.*;
import com.smarthome.management.platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceHistoryRepository deviceHistoryRepository;
    private final EnergyConsumptionRepository energyConsumptionRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_USER)));
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_ADMIN)));

        if (!userRepository.existsByEmail("admin@smarthome.local")) {
            User admin = new User();
            admin.setFullName("Smart Home Admin");
            admin.setEmail("admin@smarthome.local");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.getRoles().add(userRole);
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("user@smarthome.local")) {
            User user = new User();
            user.setFullName("Demo User");
            user.setEmail("user@smarthome.local");
            user.setPassword(passwordEncoder.encode("user123"));
            user.getRoles().add(userRole);
            userRepository.save(user);
        }

        userRepository.findByEmail("user@smarthome.local")
                .filter(user -> roomRepository.countByOwner(user) == 0)
                .ifPresent(this::seedDemoHome);
    }

    private void seedDemoHome(User user) {
        List<Room> rooms = roomRepository.saveAll(List.of(
                room(user, "Living Room", "Ground Floor", "Main family area with entertainment devices."),
                room(user, "Kitchen", "Ground Floor", "Cooking area with sensors and energy monitoring."),
                room(user, "Bedroom", "First Floor", "Private sleeping room with climate control."),
                room(user, "Office", "First Floor", "Workspace with automation and monitoring."),
                room(user, "Garage", "Basement", "Parking and storage space."),
                room(user, "Garden", "Outdoor", "Outdoor security and environment monitoring.")
        ));

        List<Device> devices = deviceRepository.saveAll(List.of(
                device(rooms.get(0), "Living Room Ceiling Light", DeviceType.LIGHT, DeviceStatus.ON, DeviceTier.STANDARD,
                        true, "12.40", "22.50", "Warm", "Living Room ceiling"),
                device(rooms.get(1), "Kitchen Energy Meter", DeviceType.ENERGY_METER, DeviceStatus.ON, DeviceTier.PREMIUM,
                        true, "4.80", "23.10", "Monitoring", "Kitchen electrical panel"),
                device(rooms.get(2), "Bedroom Thermostat", DeviceType.THERMOSTAT, DeviceStatus.STANDBY, DeviceTier.PREMIUM,
                        true, "2.10", "21.00", "Eco", "Bedroom wall"),
                device(rooms.get(3), "Office Motion Sensor", DeviceType.SENSOR, DeviceStatus.ON, DeviceTier.STANDARD,
                        true, "0.60", "22.00", "Active", "Office entrance"),
                device(rooms.get(4), "Garage Smart Door", DeviceType.SMART_DOOR, DeviceStatus.OFF, DeviceTier.PREMIUM,
                        true, "1.30", "16.80", "Locked", "Garage door"),
                device(rooms.get(5), "Garden Security Camera", DeviceType.CAMERA, DeviceStatus.ON, DeviceTier.PREMIUM,
                        true, "6.50", "18.40", "Recording", "Garden gate")
        ));

        deviceHistoryRepository.saveAll(List.of(
                history(devices.get(0), ActivityType.TURNED_ON, DeviceStatus.OFF, DeviceStatus.ON, "Living room light was turned on."),
                history(devices.get(1), ActivityType.CREATED, null, DeviceStatus.ON, "Kitchen energy meter was registered."),
                history(devices.get(2), ActivityType.MODE_CHANGED, DeviceStatus.ON, DeviceStatus.STANDBY, "Bedroom thermostat switched to Eco mode."),
                history(devices.get(3), ActivityType.UPDATED, DeviceStatus.ON, DeviceStatus.ON, "Office sensor sensitivity was updated."),
                history(devices.get(4), ActivityType.TURNED_OFF, DeviceStatus.ON, DeviceStatus.OFF, "Garage door automation was locked."),
                history(devices.get(5), ActivityType.IMPORTED, null, DeviceStatus.ON, "Garden camera was imported from external provider.")
        ));

        energyConsumptionRepository.saveAll(List.of(
                consumption(devices.get(0), "1.20", "0.42", 1),
                consumption(devices.get(1), "5.80", "2.03", 2),
                consumption(devices.get(2), "2.35", "0.82", 3),
                consumption(devices.get(3), "0.45", "0.16", 4),
                consumption(devices.get(4), "0.90", "0.32", 5),
                consumption(devices.get(5), "3.70", "1.30", 6)
        ));

        notificationRepository.saveAll(List.of(
                notification(user, "Welcome", "Demo smart home data has been initialized.", false, 1),
                notification(user, "Energy report", "Kitchen energy meter reported higher usage today.", false, 2),
                notification(user, "Thermostat mode", "Bedroom thermostat is running in Eco mode.", true, 3),
                notification(user, "Sensor update", "Office motion sensor is active and online.", true, 4),
                notification(user, "Garage locked", "Garage smart door is locked.", false, 5),
                notification(user, "Camera online", "Garden security camera is recording.", false, 6)
        ));
    }

    private Room room(User owner, String name, String floor, String description) {
        Room room = new Room();
        room.setOwner(owner);
        room.setName(name);
        room.setFloor(floor);
        room.setDescription(description);
        return room;
    }

    private Device device(Room room, String name, DeviceType type, DeviceStatus status, DeviceTier tier,
                          boolean online, String energyUsage, String temperature, String mode, String location) {
        Device device = new Device();
        device.setRoom(room);
        device.setName(name);
        device.setType(type);
        device.setStatus(status);
        device.setTier(tier);
        device.setOnline(online);
        device.setEnergyUsage(new BigDecimal(energyUsage));
        device.setTemperature(new BigDecimal(temperature));
        device.setMode(mode);
        device.setLocation(location);
        return device;
    }

    private DeviceHistory history(Device device, ActivityType activityType, DeviceStatus oldStatus,
                                  DeviceStatus newStatus, String message) {
        DeviceHistory history = new DeviceHistory();
        history.setDevice(device);
        history.setActivityType(activityType);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setMessage(message);
        return history;
    }

    private EnergyConsumption consumption(Device device, String kilowattHours, String estimatedCost, int daysAgo) {
        EnergyConsumption consumption = new EnergyConsumption();
        consumption.setDevice(device);
        consumption.setKilowattHours(new BigDecimal(kilowattHours));
        consumption.setEstimatedCost(new BigDecimal(estimatedCost));
        consumption.setRecordedAt(LocalDateTime.now().minusDays(daysAgo));
        return consumption;
    }

    private Notification notification(User user, String title, String message, boolean readFlag, int hoursAgo) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setReadFlag(readFlag);
        notification.setCreatedAt(LocalDateTime.now().minusHours(hoursAgo));
        return notification;
    }
}