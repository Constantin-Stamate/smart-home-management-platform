package com.smarthome.management.platform.service;

import com.smarthome.management.platform.dto.DeviceRequest;
import com.smarthome.management.platform.model.entity.Device;
import com.smarthome.management.platform.model.entity.Room;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.model.enums.ActivityType;
import com.smarthome.management.platform.model.enums.DeviceStatus;
import com.smarthome.management.platform.patterns.behavioral.command.DeviceCommand;
import com.smarthome.management.platform.patterns.behavioral.command.DeviceCommandFactory;
import com.smarthome.management.platform.patterns.behavioral.command.DeviceCommandInvoker;
import com.smarthome.management.platform.patterns.behavioral.observer.DeviceEvent;
import com.smarthome.management.platform.patterns.behavioral.observer.DeviceEventPublisher;
import com.smarthome.management.platform.patterns.creational.abstractfactory.DeviceFamilyFactoryProvider;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfiguration;
import com.smarthome.management.platform.patterns.creational.builder.DeviceConfigurationBuilder;
import com.smarthome.management.platform.patterns.creational.factorymethod.DeviceFactoryRegistry;
import com.smarthome.management.platform.patterns.creational.prototype.DeviceConfigurationPrototype;
import com.smarthome.management.platform.patterns.structural.adapter.DeviceImportAdapter;
import com.smarthome.management.platform.patterns.structural.adapter.ExternalDeviceClient;
import com.smarthome.management.platform.patterns.structural.decorator.AuditDecorator;
import com.smarthome.management.platform.patterns.structural.decorator.BaseDeviceOperation;
import com.smarthome.management.platform.patterns.structural.decorator.DeviceOperation;
import com.smarthome.management.platform.patterns.structural.decorator.EnergyTrackingDecorator;
import com.smarthome.management.platform.repository.DeviceRepository;
import com.smarthome.management.platform.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final RoomRepository roomRepository;
    private final DeviceFamilyFactoryProvider familyFactoryProvider;
    private final DeviceFactoryRegistry factoryRegistry;
    private final DeviceConfigurationPrototype prototype;
    private final DeviceCommandFactory commandFactory;
    private final DeviceEventPublisher eventPublisher;
    private final EnergyService energyService;
    private final HistoryService historyService;
    private final ExternalDeviceClient externalDeviceClient;
    private final DeviceImportAdapter externalDeviceAdapter;
    private final DeviceCommandInvoker commandInvoker;

    @Transactional(readOnly = true)
    public List<Device> findFor(User owner) {
        return deviceRepository.findByRoomOwnerOrderByNameAsc(owner);
    }

    @Transactional(readOnly = true)
    public Device getOwned(Long id, User owner) {
        return deviceRepository.findByIdAndRoomOwner(id, owner).orElseThrow(() -> new IllegalArgumentException("Dispozitivul nu exista."));
    }

    @Transactional
    public Device create(DeviceRequest request, User owner) {
        Room room = roomRepository.findByIdAndOwner(request.getRoomId(), owner).orElseThrow(() -> new IllegalArgumentException("Camera nu exista."));
        DeviceConfiguration defaults = familyFactoryProvider.forTier(request.getTier()).defaultConfiguration(request.getName(), request.getType(), request.getLocation());
        DeviceConfiguration configuration = new DeviceConfigurationBuilder().name(defaults.name()).type(defaults.type()).tier(defaults.tier()).mode(request.getMode() == null || request.getMode().isBlank() ? defaults.mode() : request.getMode()).location(defaults.location()).energyUsage(defaults.energyUsage()).temperature(defaults.temperature()).build();

        Device device = factoryRegistry.createDevice(configuration);
        device.setRoom(room);
        Device saved = deviceRepository.save(device);
        historyService.record(saved, ActivityType.CREATED, null, saved.getStatus(), "Dispozitiv creat: " + saved.getName());
        return saved;
    }

    @Transactional
    public void update(Long id, DeviceRequest request, User owner) {
        Device device = getOwned(id, owner);
        Room room = roomRepository.findByIdAndOwner(request.getRoomId(), owner).orElseThrow(() -> new IllegalArgumentException("Camera nu exista."));
        device.setName(request.getName());
        device.setType(request.getType());
        device.setTier(request.getTier());
        device.setRoom(room);
        device.setMode(request.getMode());
        device.setLocation(request.getLocation());
        deviceRepository.save(device);
        historyService.record(device, ActivityType.UPDATED, device.getStatus(), device.getStatus(), "Dispozitiv actualizat: " + device.getName());
    }

    @Transactional
    public void delete(Long id, User owner) {
        Device device = getOwned(id, owner);
        deviceRepository.delete(device);
    }

    @Transactional
    public Device cloneDevice(Long id, User owner) {
        Device source = getOwned(id, owner);
        Device clone = prototype.copyFrom(source, source.getName() + " Copy");
        clone.setRoom(source.getRoom());
        Device saved = deviceRepository.save(clone);
        historyService.record(saved, ActivityType.CREATED, null, saved.getStatus(), "Configuratie clonata din " + source.getName());
        return saved;
    }

    @Transactional
    public void turnOn(Long id, User owner) {
        control(id, owner, true);
    }

    @Transactional
    public void turnOff(Long id, User owner) {
        control(id, owner, false);
    }

    @Transactional
    public void changeMode(Long id, String mode, User owner) {
        Device device = getOwned(id, owner);
        commandInvoker.execute(commandFactory.changeMode(device, mode));
        historyService.record(device, ActivityType.MODE_CHANGED, device.getStatus(), device.getStatus(), "Mod schimbat in " + mode);
        deviceRepository.save(device);
    }

    @Transactional
    public int importExternalDevices(User owner) {
        Room fallbackRoom = roomRepository.findByOwnerOrderByNameAsc(owner).stream().findFirst().orElseGet(() -> {
            Room room = new Room();
            room.setOwner(owner);
            room.setName("Import extern");
            room.setFloor("Parter");
            return roomRepository.save(room);
        });

        List<Device> imported = externalDeviceClient.fetchDemoDevices().stream().map(externalDeviceAdapter::adapt).peek(device -> device.setRoom(fallbackRoom)).map(deviceRepository::save).toList();
        imported.forEach(device -> historyService.record(device, ActivityType.IMPORTED, null, device.getStatus(), "Dispozitiv importat prin adapter: " + device.getName()));
        return imported.size();
    }

    private void control(Long id, User owner, boolean turnOn) {
        Device device = getOwned(id, owner);
        DeviceStatus oldStatus = device.getStatus();

        DeviceCommand command = turnOn ? commandFactory.turnOn(device) : commandFactory.turnOff(device);

        DeviceOperation operation = new AuditDecorator(new EnergyTrackingDecorator(new BaseDeviceOperation(target -> commandInvoker.execute(command))));
        operation.execute(device);

        energyService.record(device);
        deviceRepository.save(device);
        DeviceStatus newStatus = device.getStatus();
        String message = device.getName() + " a trecut din " + oldStatus + " in " + newStatus;
        eventPublisher.publish(new DeviceEvent(device, oldStatus, newStatus, message));
    }
}