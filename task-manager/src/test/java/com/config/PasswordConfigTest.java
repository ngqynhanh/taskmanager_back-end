// File: src/test/java/com/taskmanagement/config/PasswordConfigTest.java
package com.taskmanagement.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PasswordConfigTest {

    @Test
    void passwordEncoder_isBCrypt() {
        PasswordConfig cfg = new PasswordConfig();
        PasswordEncoder encoder = cfg.passwordEncoder();

        assertNotNull(encoder);
        assertTrue(encoder instanceof BCryptPasswordEncoder);
        // basic behavior check
        String raw = "secret";
        String enc = encoder.encode(raw);
        assertTrue(encoder.matches(raw, enc));
    }
}