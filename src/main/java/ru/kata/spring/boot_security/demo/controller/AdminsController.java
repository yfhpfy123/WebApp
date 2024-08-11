package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UsersValidator;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final UsersValidator validator;
    private final UserService userServiceImpl;

    @Autowired
    public AdminsController(UsersValidator validator, UserService userService) {
        this.validator = validator;
        this.userServiceImpl = userService;
    }
    @GetMapping()
    public String findAll(ModelMap model) {
        model.addAttribute("users", userServiceImpl.findAll());
        return "admin/all";
    }

    @GetMapping("/user")
    public String findOne(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("user", userServiceImpl.findOne(id));
        return "users/user";
    }
    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user, ModelMap model) {
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        model.addAttribute("roles", roles);
        return "admin/new";
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelMap model) {
        validator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
            model.addAttribute("roles", roles);
            return "admin/new";
        }

        userServiceImpl.save(user);
        return "redirect:/admin";
    }


    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, ModelMap model) {
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        model.addAttribute("roles", roles);
        model.addAttribute("user", userServiceImpl.findOne(id));
        return "admin/edit";
    }

    @PostMapping("/edit")
    public String update(@RequestParam("id") Long id, ModelMap model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
            model.addAttribute("roles", roles);
            model.addAttribute("user", user);
            return "admin/edit";
        }
        userServiceImpl.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        User user = userServiceImpl.findOne(id);

        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден!");
        }
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }

}
