package com.smarthome.management.platform.controller;

import com.smarthome.management.platform.patterns.structural.facade.SmartHomeFacade;
import com.smarthome.management.platform.utils.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final SmartHomeFacade smartHomeFacade;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("view", smartHomeFacade.dashboard(currentUserProvider.getCurrentUser()));
        return "dashboard/index";
    }
}