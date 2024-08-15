package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
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

        userService.save(new User(adminName, adminSurname, age, adminUsername , User.ADMIN.getPassword(), User.ADMIN.getRoles()));

        for (int i = 0; i < 100; i++) {
            users = userService.findAll();
            lastUserId = users.isEmpty() ? 0 : users.get(users.size() - 1).getId();
            String userName = String.format("user%d", lastUserId + 1);
            String userSurname = String.format("user%d", lastUserId + 1);
            String userUsername = String.format("user%d@mail.ru", lastUserId + 1);
            age = (int) (Math.random() * (55 - 18 + 1)) + 18;
            userService.save(new User(userName, userSurname, age, userUsername, User.USER.getPassword(), User.USER.getRoles()));
        }
    }
}
