package com.proyecto.sistemas_op_umg2025.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sistemas_op_umg2025.model.entity.Specialty;
import com.proyecto.sistemas_op_umg2025.model.repository.SpecialtyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialtyService implements ServiceCRUD<Specialty> {
    
    @Autowired
    private SpecialtyRepository repository;

    @Override
    public Specialty createOrUpdate(Specialty value) {
        return repository.save(value);
    }

    @Override
    public List<Specialty> getDataList() {
        return repository.findAll();
    }

    @Override
    public Specialty getFindUncle(Long value) {
        return repository.findById(value).orElse(null);
    }


    @Override
    public void deleteFind(Specialty value) {
        repository.delete(value);
    }
}
