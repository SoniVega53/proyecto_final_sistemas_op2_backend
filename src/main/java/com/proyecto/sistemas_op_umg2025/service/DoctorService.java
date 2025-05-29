package com.proyecto.sistemas_op_umg2025.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sistemas_op_umg2025.model.entity.Doctor;
import com.proyecto.sistemas_op_umg2025.model.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService implements ServiceCRUD<Doctor> {

    @Autowired
    private DoctorRepository repository;

    @Override
    public Doctor createOrUpdate(Doctor value) {
        return repository.save(value);
    }

    @Override
    public List<Doctor> getDataList() {
        return repository.findAll();
    }

    @Override
    public Doctor getFindUncle(Long value) {
        return repository.findById(value).orElse(null);
    }

    public Doctor getFindByName(String name) {
        return repository.findByName(name).orElseThrow();
    }

    @Override
    public void deleteFind(Doctor value) {
        repository.delete(value);
    }
}
