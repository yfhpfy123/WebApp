package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class UsersController {

    private final UserService userServiceImpl;

    @Autowired
    public UsersController(UserService userService) {
        this.userServiceImpl = userService;
    }

    @GetMapping("/user")
    public String findOne(@AuthenticationPrincipal UserDetails details, ModelMap model) {
        model.addAttribute("auth", userServiceImpl.findByUsername(details.getUsername()));
        return "info";
    }
}
