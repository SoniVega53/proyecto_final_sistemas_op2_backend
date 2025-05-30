package com.proyecto.sistemas_op_umg2025.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistemas_op_umg2025.model.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
