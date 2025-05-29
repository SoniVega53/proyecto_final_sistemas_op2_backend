package com.proyecto.sistemas_op_umg2025.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sistemas_op_umg2025.model.entity.Prescription;
import com.proyecto.sistemas_op_umg2025.model.repository.PrescriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionService implements ServiceCRUD<Prescription> {

    @Autowired
    private PrescriptionRepository repository;

    @Override
    public Prescription createOrUpdate(Prescription value) {
        return repository.save(value);
    }

    @Override
    public List<Prescription> getDataList() {
        return repository.findAll();
    }

    @Override
    public Prescription getFindUncle(Long value) {
        return repository.findById(value).orElse(null);
    }

    public List<Prescription> getByAppointmentId(Long appointmentId) {
        return repository.findByAppointmentId(appointmentId);
    }

    @Override
    public void deleteFind(Prescription value) {
        repository.delete(value);
    }
}