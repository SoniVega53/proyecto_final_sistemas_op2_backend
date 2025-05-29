package com.proyecto.sistemas_op_umg2025.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistemas_op_umg2025.model.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByName(String name);
    boolean existsByName(String name);
}