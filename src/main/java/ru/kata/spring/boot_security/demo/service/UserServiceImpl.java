package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;


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
        user.setPassword(encoder.encode(user.getPassword()));
        if (getRoles() == null || getRoles().isEmpty()){
            rolesRepository.save(new Role("ADMIN"));
            rolesRepository.save(new Role("USER"));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(getRoleByName("USER")));
        }
        Set<Role> roles = new HashSet<>(rolesRepository.findAll());
        for (Role role : roles) {
            role.getUsers().add(user);
            rolesRepository.save(role);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, User updatedUser) {
        User currentUser = findOne(id);

        String currentPassword = currentUser.getPassword();
        if (!currentPassword.equals(updatedUser.getPassword())) {
            updatedUser.setPassword(encoder.encode(updatedUser.getPassword()));
        }

        Set<Role> currentRoles = currentUser.getRoles();
        if (updatedUser.getRoles().isEmpty()) {
            updatedUser.setRoles(currentRoles);
        }
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
    @Transactional
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Override
    public List<Role> getRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return rolesRepository.findByName(name).orElse(null);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        rolesRepository.save(role);
    }

}
