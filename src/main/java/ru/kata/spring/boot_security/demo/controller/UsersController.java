package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userServiceImpl;

    @Autowired
    public UsersController(UserService userService) {
        this.userServiceImpl = userService;
    }

    @GetMapping("/user")
    public String findOne(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("user", userServiceImpl.findOne(id));
        return "users/user";
    }
}
