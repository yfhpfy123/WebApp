package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UsersValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminsController {

    private final UsersValidator validator;
    private final UserService userService;

    @Autowired
    public AdminsController(UsersValidator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, "roles", new CustomCollectionEditor(Set.class) {
            @Override
            protected Object convertElement(Object element) {
                String roleName = (String) element;
                return userService.getRoleByName(roleName);
            }
        });
    }
    @GetMapping
    public String showAdminPanel(@ModelAttribute("user") User user, ModelMap model, @AuthenticationPrincipal UserDetails details) {
        model.addAttribute("roles", userService.getRoles());
        model.addAttribute("auth", userService.findByUsername(details.getUsername()));
        model.addAttribute("users", userService.findAll());
        return "admin_panel";
    }
    @PostMapping("/new")
    public String add(@AuthenticationPrincipal UserDetails details, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelMap model) {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error " + bindingResult);
            model.addAttribute("roles", userService.getRoles());
            model.addAttribute("auth", userService.findByUsername(details.getUsername()));
            model.addAttribute("users", userService.findAll());
            return "redirect:/admin#new";
        }
        userService.save(user);
        System.out.println("11");
        return "redirect:/admin";
    }
    @PostMapping("/edit")
    public String edit(@AuthenticationPrincipal UserDetails details, @RequestParam("id") Long id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error " + bindingResult);
            model.addAttribute("roles", userService.getRoles());
            model.addAttribute("auth", userService.findByUsername(details.getUsername()));
            model.addAttribute("users", userService.findAll());
            return "admin_panel";
        }
        userService.update(id,user);
        return "redirect:/admin";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
