package com.smarthome.management.platform.controller;

import com.smarthome.management.platform.dto.DeviceRequest;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.model.enums.DeviceTier;
import com.smarthome.management.platform.model.enums.DeviceType;
import com.smarthome.management.platform.service.DeviceService;
import com.smarthome.management.platform.service.RoomService;
import com.smarthome.management.platform.utils.CurrentUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final RoomService roomService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public String devices(Model model) {
        User user = currentUserProvider.getCurrentUser();
        populateModel(model, user, new DeviceRequest());
        return "devices/index";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute DeviceRequest deviceRequest, BindingResult bindingResult, Model model) {
        User user = currentUserProvider.getCurrentUser();

        if (bindingResult.hasErrors()) {
            populateModel(model, user, deviceRequest);
            return "devices/index";
        }

        deviceService.create(deviceRequest, user);
        return "redirect:/devices";
    }

    @PostMapping("/{id}/on")
    public String on(@PathVariable Long id) {
        deviceService.turnOn(id, currentUserProvider.getCurrentUser());
        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/off")
    public String off(@PathVariable Long id) {
        deviceService.turnOff(id, currentUserProvider.getCurrentUser());
        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/mode")
    public String mode(@PathVariable Long id, @RequestParam String mode) {
        deviceService.changeMode(id, mode, currentUserProvider.getCurrentUser());
        return "redirect:/devices";
    }

    @PostMapping("/{id}/clone")
    public String clone(@PathVariable Long id) {
        deviceService.cloneDevice(id, currentUserProvider.getCurrentUser());
        return "redirect:/devices";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        deviceService.delete(id, currentUserProvider.getCurrentUser());
        return "redirect:/devices";
    }

    @PostMapping("/import")
    public String importExternal() {
        deviceService.importExternalDevices(currentUserProvider.getCurrentUser());
        return "redirect:/devices";
    }

    private void populateModel(Model model, User user, DeviceRequest request) {
        model.addAttribute("devices", deviceService.findFor(user));
        model.addAttribute("rooms", roomService.findFor(user));
        model.addAttribute("deviceRequest", request);
        model.addAttribute("types", DeviceType.values());
        model.addAttribute("tiers", DeviceTier.values());
    }
}