package com.smarthome.management.platform.controller;

import com.smarthome.management.platform.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("users", adminService.users());
        model.addAttribute("devices", adminService.devices());
        return "admin/index";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        adminService.toggleUser(id);
        return "redirect:/admin";
    }
}