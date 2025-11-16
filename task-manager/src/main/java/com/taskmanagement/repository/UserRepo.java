package com.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taskmanagement.entity.User;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}