package com.smarthome.management.platform.controller;

import com.smarthome.management.platform.dto.ProfileRequest;
import com.smarthome.management.platform.model.entity.User;
import com.smarthome.management.platform.service.UserService;
import com.smarthome.management.platform.utils.CurrentUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final CurrentUserProvider currentUserProvider;
    private final UserService userService;

    @GetMapping
    public String profile(Model model) {
        User user = currentUserProvider.getCurrentUser();
        ProfileRequest request = new ProfileRequest();
        request.setFullName(user.getFullName());
        model.addAttribute("profileRequest", request);
        model.addAttribute("user", user);
        return "profile/index";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute ProfileRequest profileRequest, BindingResult bindingResult, Model model) {
        User user = currentUserProvider.getCurrentUser();

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "profile/index";
        }

        userService.updateProfile(user, profileRequest);
        return "redirect:/profile";
    }
}