package com.jupitters.book_network.repository;

import com.jupitters.book_network.model.User;
import com.jupitters.book_network.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
