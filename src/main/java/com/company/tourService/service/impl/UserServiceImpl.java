package com.company.tourService.service.impl;

import com.company.tourService.model.Role;
import com.company.tourService.model.Status;
import com.company.tourService.model.User;
import com.company.tourService.repos.RoleRepos;
import com.company.tourService.repos.UserRepos;
import com.company.tourService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepos userRepos;
    private final RoleRepos roleRepos;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepos userRepos, RoleRepos roleRepos, BCryptPasswordEncoder passwordEncoder) {
        this.userRepos = userRepos;
        this.roleRepos = roleRepos;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepos.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepos.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepos.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepos.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepos.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepos.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }
}