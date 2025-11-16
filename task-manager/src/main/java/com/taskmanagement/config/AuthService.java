package com.taskmanagement.config;

import com.taskmanagement.entity.User;
import com.taskmanagement.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user, String rawPassword) {
        String hashed = passwordEncoder.encode(rawPassword);
        user.setPasswordHash(hashed);
        return userRepo.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public boolean authenticate(String username, String rawPassword) {
        return userRepo.findByUsername(username)
                .map(u -> passwordEncoder.matches(rawPassword, u.getPasswordHash()))
                .orElse(false);
    }
}
