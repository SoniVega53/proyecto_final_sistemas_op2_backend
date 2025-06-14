package com.proyecto.sistemas_op_umg2025.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.sistemas_op_umg2025.model.entity.DatesDoctor;

public interface DatesDoctorRepository extends JpaRepository<DatesDoctor, Long> {
    List<DatesDoctor> findByDoctorId(Long doctorId);
}
