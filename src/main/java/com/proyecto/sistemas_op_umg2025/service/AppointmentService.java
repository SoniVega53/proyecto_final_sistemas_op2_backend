package com.proyecto.sistemas_op_umg2025.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    //@Cacheable(value = "appointmentsByDoctor", key = "#id_doc")
    public List<Appointment> getByDoctorId(Long id_doc) {
        return repository.findByDoctorId(id_doc);
    }

    //@Cacheable(value = "appointmentsByPaciente", key = "#id_pac")
    public List<Appointment> getByPatientId(Long id_pac) {
        return repository.findByPatientId(id_pac);
    }

    @Override
    public void deleteFind(Appointment value) {
        repository.delete(value);
    }
}
