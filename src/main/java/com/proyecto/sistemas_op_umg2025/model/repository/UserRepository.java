package com.proyecto.sistemas_op_umg2025.model.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistemas_op_umg2025.model.entity.User;

            
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
