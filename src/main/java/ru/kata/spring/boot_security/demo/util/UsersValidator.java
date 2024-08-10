package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Component
public class UsersValidator implements Validator {
    private final UserRepository repo;
    @Autowired
    public UsersValidator(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Пользователь с таким логином уже существует.");
        }
    }

    public void unValidate(Object target, Errors errors) {
        User user = (User) target;
        if (!repo.findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Пользователь с таким логином не существует.");
        }
    }
}
