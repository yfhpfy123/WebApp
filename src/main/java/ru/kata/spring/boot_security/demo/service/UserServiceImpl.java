package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private  final RolesRepository rolesRepository;

        @Autowired
    public UserServiceImpl(PasswordEncoder encoder, UserRepository userRepository, RolesRepository rolesRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
            this.rolesRepository = rolesRepository;
        }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {
        if (rolesRepository.findAll().isEmpty()) {
            rolesRepository.save(new Role("USER"));
            rolesRepository.save(new Role("ADMIN"));
        }
        Set<Role> defaultRole = Collections.singleton(rolesRepository.getById(2L));
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRoles() == null) {
            user.setRoles(defaultRole);
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, User updatedUser) {
        updatedUser.setPassword(encoder.encode(updatedUser.getPassword()));
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Пользователь не найден!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
