package com.proyecto.sistemas_op_umg2025.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sistemas_op_umg2025.model.entity.Appointment;
import com.proyecto.sistemas_op_umg2025.model.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService implements ServiceCRUD<Appointment> {

    @Autowired
    private AppointmentRepository repository;

    @Override
    public Appointment createOrUpdate(Appointment value) {
        return repository.save(value);
    }

    @Override
    public List<Appointment> getDataList() {
        return repository.findAll();
    }

    @Override
    public Appointment getFindUncle(Long value) {
        return repository.findById(value).orElse(null);
    }

    public List<Appointment> getByDoctorId(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }

    public List<Appointment> getByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    @Override
    public void deleteFind(Appointment value) {
        repository.delete(value);
    }
}
