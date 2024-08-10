package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.util.UsersValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UsersValidator validator;
    private final UserServiceImpl userServiceImpl;
    @Autowired
    public AuthController(UsersValidator validator, UserServiceImpl userServiceImpl) {
        this.validator = validator;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/login")
    public String getloginPage() {
        return "auth/login";
    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user ) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        validator.validate(user, bindingResult);


        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        userServiceImpl.save(user);
        return "redirect:/auth/login";

    }

}
