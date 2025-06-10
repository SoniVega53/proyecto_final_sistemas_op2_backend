package com.proyecto.sistemas_op_umg2025.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistemas_op_umg2025.model.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByAppointmentId(Long appointmentId);
    List<Prescription> findByAppointmentDoctorId(Long doctorId);
}
