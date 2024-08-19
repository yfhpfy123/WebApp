package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
public class TemplateController {

    @GetMapping("/")
    public String getloginPage() {
        return "login";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin")
    public String showSidebar() {
        return "navigate";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin_panel")
    public String showAdminPanel() {
        return "admin";
    }

    @GetMapping("/user_info")
    public String findOne() {
        return "user";
    }

    @GetMapping("user")
    public String user(){
        return "info";
    }
}
