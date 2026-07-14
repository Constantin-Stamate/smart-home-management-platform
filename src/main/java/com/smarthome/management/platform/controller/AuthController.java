package com.smarthome.management.platform.controller;

import com.smarthome.management.platform.dto.RegisterRequest;
import com.smarthome.management.platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping({"/register", "/auth/register"})
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping({"/register", "/auth/register"})
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            userService.register(registerRequest);
            redirectAttributes.addFlashAttribute("success", "Cont creat. Te poti autentifica.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("registerError", ex.getMessage());
            return "auth/register";
        }
    }
}