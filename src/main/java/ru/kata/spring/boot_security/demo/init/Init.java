package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;

    @Autowired
    public Init(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        List<User> users = userService.findAll();
        Long lastUserId = users.isEmpty() ? 0 : users.get(users.size() - 1).getId();
        String adminName = String.format("admin%d", lastUserId + 1);
        String adminSurname = String.format("admin%d", lastUserId + 1);
        int age = (int) (Math.random() * (55 - 18 + 1)) + 18;
        String adminUsername = String.format("admin%d@mail.ru", lastUserId + 1);
        if (userService.getRoles() == null || userService.getRoles().isEmpty()) {
            userService.addRole(Role.ADMIN);
            userService.addRole(Role.USER);
        }

        userService.save(new User(adminName, adminSurname, age, adminUsername , User.ADMIN.getPassword(), Set.of(userService.getRoleByName(Role.ADMIN.getName()), userService.getRoleByName(Role.USER.getName()))));

        for (int i = 0; i < 15; i++) {
            users = userService.findAll();
            lastUserId = users.isEmpty() ? 0 : users.get(users.size() - 1).getId();
            String userName = String.format("user%d", lastUserId + 1);
            String userSurname = String.format("user%d", lastUserId + 1);
            age = (int) (Math.random() * (55 - 18 + 1)) + 18;
            String userUsername = String.format("user%d@mail.ru", lastUserId + 1);
            Set<Role> userRoles = Collections.singleton(userService.getRoleByName(User.USER.getName()));

            userService.save(new User(userName, userSurname, age, userUsername, User.USER.getPassword(), userRoles));
        }
    }
}
