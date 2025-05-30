package com.proyecto.sistemas_op_umg2025.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sistemas_op_umg2025.model.entity.Patient;
import com.proyecto.sistemas_op_umg2025.model.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService implements ServiceCRUD<Patient> {

    @Autowired
    private PatientRepository repository;

    @Override
    public Patient createOrUpdate(Patient value) {
        return repository.save(value);
    }

    @Override
    public List<Patient> getDataList() {
        return repository.findAll();
    }

    @Override
    public Patient getFindUncle(Long value) {
        return repository.findById(value).orElse(null);
    }


    @Override
    public void deleteFind(Patient value) {
        repository.delete(value);
    }
}