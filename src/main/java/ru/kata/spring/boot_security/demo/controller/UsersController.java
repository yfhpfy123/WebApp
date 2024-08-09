package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userServiceImpl;

    @Autowired
    public UsersController(UserService userService) {
        this.userServiceImpl = userService;
    }
    @GetMapping()
    public String findAll(ModelMap model) {
        model.addAttribute("users", userServiceImpl.findAll());
        return "users/all";
    }

    @GetMapping("/user")
    public String findOne(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("user", userServiceImpl.findOne(id));
        return "users/user";
    }

    @GetMapping("/new")
    public String createUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user) {
        userServiceImpl.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("user", userServiceImpl.findOne(id));
        return "users/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user, @RequestParam("id") Long id) {
        userServiceImpl.update(id, user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        User user = userServiceImpl.findOne(id);

        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден!");
        }
        userServiceImpl.delete(id);
        return "redirect:/users";
    }


}
