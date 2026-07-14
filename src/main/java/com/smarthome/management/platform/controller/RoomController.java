package com.smarthome.management.platform.controller;

import com.smarthome.management.platform.dto.RoomRequest;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.service.RoomService;
import com.smarthome.management.platform.utils.CurrentUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public String rooms(Model model) {
        User user = currentUserProvider.getCurrentUser();
        model.addAttribute("rooms", roomService.findFor(user));
        model.addAttribute("roomRequest", new RoomRequest());
        return "rooms/index";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute RoomRequest roomRequest, BindingResult bindingResult, Model model) {
        User user = currentUserProvider.getCurrentUser();

        if (bindingResult.hasErrors()) {
            model.addAttribute("rooms", roomService.findFor(user));
            return "rooms/index";
        }

        roomService.create(roomRequest, user);
        return "redirect:/rooms";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        roomService.delete(id, currentUserProvider.getCurrentUser());
        return "redirect:/rooms";
    }
}