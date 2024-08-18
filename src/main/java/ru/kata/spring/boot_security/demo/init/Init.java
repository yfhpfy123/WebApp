package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class Init {

    private final UserService userService;

    @Autowired
    public Init(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        userService.addRole(new Role("ADMIN"));
        userService.addRole(new Role("USER"));
        List<User> users = userService.findAll();
        Long lastUserId = users.isEmpty() ? null : users.get(users.size() - 1).getId();

        String adminName = lastUserId == null ? "admin" : String.format("admin%d", lastUserId);
        String adminSurname = lastUserId == null ? "admin" : String.format("admin%d", lastUserId);
        String adminUsername = lastUserId == null ? "admin@mail.ru" : String.format("admin%d@mail.ru", lastUserId);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(userService.getRoleByName("ADMIN"));
        adminRoles.add(userService.getRoleByName("USER"));

        User admin = new User(
                adminName, // ----------------------------------------------------- Name
                adminSurname, // -------------------------------------------------- Surname
                (int) ((Math.random() * (55 - 18 + 1)) + 18), // ------------------ Age
                adminUsername, // ------------------------------------------------- Username
                "1", // ----------------------------------------------------------- Password
                adminRoles // ----------------------------------------------------- Roles
        );

        userService.save(admin);

        for (int i = 0; i < 15; i++) {
            users = userService.findAll();
            lastUserId = users.isEmpty() ? 0 : users.get(users.size() - 1).getId();

            String userName = lastUserId == null ? "user" : String.format("user%d", lastUserId);
            String userSurname = lastUserId == null ? "user" : String.format("user%d", lastUserId);
            String userUsername = lastUserId == null ? "user@mail.ru" : String.format("user%d@mail.ru", lastUserId);

            userService.save(new User(
                    userName, // -------------------------------------------------------------------- Name
                    userSurname, // ----------------------------------------------------------------- Surname
                    (int) ((Math.random() * (55 - 18 + 1)) + 18), // -------------------------------- Age
                    userUsername, // ---------------------------------------------------------------- Username
                    "1", // ------------------------------------------------------------------------- Password
                    Collections.singleton(userService.getRoleByName("USER")) // --------------------- Roles
            ));
        }
    }
}
