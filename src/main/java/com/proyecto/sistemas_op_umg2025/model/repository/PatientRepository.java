package com.proyecto.sistemas_op_umg2025.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistemas_op_umg2025.model.entity.Patient;
import com.proyecto.sistemas_op_umg2025.model.entity.User;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUser(User user);
}
