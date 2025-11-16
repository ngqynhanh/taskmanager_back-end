// File: src/test/java/com/taskmanagement/config/SecurityConfigTest.java
package com.taskmanagement.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    @Test
    void securityFilterChainBeanIsPresent() {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
            // register minimal config classes; add PasswordConfig because SecurityConfig often depends on a PasswordEncoder bean
            ctx.register(SecurityConfig.class, PasswordConfig.class);
            ctx.refresh();

            SecurityFilterChain chain = ctx.getBean(SecurityFilterChain.class);
            assertNotNull(chain);
        }
    }
}