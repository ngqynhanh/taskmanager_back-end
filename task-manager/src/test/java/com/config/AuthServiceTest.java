// File: src/test/java/com/taskmanagement/config/AuthServiceTest.java
package com.taskmanagement.config;

import com.taskmanagement.entity.User;
import com.taskmanagement.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUser_encodesPasswordAndSaves() {
        User input = new User();
        input.setUsername("alice");

        when(passwordEncoder.encode("raw")).thenReturn("hashed");
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = authService.registerUser(input, "raw");

        assertEquals("hashed", saved.getPasswordHash());
        verify(passwordEncoder).encode("raw");
        verify(userRepo).save(saved);
    }

    @Test
    void authenticate_returnsTrueWhenMatches() {
        User stored = new User();
        stored.setUsername("bob");
        stored.setPasswordHash("hashed");

        when(userRepo.findByUsername("bob")).thenReturn(Optional.of(stored));
        when(passwordEncoder.matches("raw", "hashed")).thenReturn(true);

        assertTrue(authService.authenticate("bob", "raw"));
        verify(passwordEncoder).matches("raw", "hashed");
    }

    @Test
    void authenticate_returnsFalseWhenNoUserOrNotMatched() {
        when(userRepo.findByUsername("nope")).thenReturn(Optional.empty());
        assertFalse(authService.authenticate("nope", "anything"));

        User stored = new User();
        stored.setPasswordHash("h");
        when(userRepo.findByUsername("carol")).thenReturn(Optional.of(stored));
        when(passwordEncoder.matches("raw", "h")).thenReturn(false);

        assertFalse(authService.authenticate("carol", "raw"));
    }

    @Test
    void findByUsername_delegatesToRepo() {
        User u = new User();
        u.setUsername("dan");
        when(userRepo.findByUsername("dan")).thenReturn(Optional.of(u));

        Optional<User> res = authService.findByUsername("dan");
        assertTrue(res.isPresent());
        assertEquals("dan", res.get().getUsername());
    }
}