package com.proyecto.sistemas_op_umg2025.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sistemas_op_umg2025.model.entity.DatesDoctor;
import com.proyecto.sistemas_op_umg2025.model.repository.DatesDoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatesDoctorService implements ServiceCRUD<DatesDoctor>{

   @Autowired
    private DatesDoctorRepository repository;

    @Override
    public DatesDoctor createOrUpdate(DatesDoctor value) {
        return repository.save(value);
    }

    @Override
    public List<DatesDoctor> getDataList() {
        return repository.findAll();
    }

    @Override
    public DatesDoctor getFindUncle(Long value) {
        return repository.findById(value).orElse(null);
    }

    public List<DatesDoctor> getByDoctorId(Long id_doc) {
        return repository.findByDoctorId(id_doc);
    }

    @Override
    public void deleteFind(DatesDoctor value) {
        repository.delete(value);
    }

}
