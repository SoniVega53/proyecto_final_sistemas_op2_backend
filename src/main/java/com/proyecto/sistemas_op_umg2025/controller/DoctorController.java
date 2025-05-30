package com.proyecto.sistemas_op_umg2025.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.sistemas_op_umg2025.model.entity.BaseResponse;
import com.proyecto.sistemas_op_umg2025.model.entity.Doctor;
import com.proyecto.sistemas_op_umg2025.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/proyecto/")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;

    @GetMapping("admin/doctors/see")
    public List<Doctor> getDataList() {
        return service.getDataList();
    }

    @DeleteMapping("doctor/eliminar/{id}")
    public ResponseEntity<BaseResponse> deleteDoctor(@PathVariable Long id) {
        try {
            Doctor find = service.getFindUncle(id);
            if (find != null) {
                service.deleteFind(find);
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("200")
                        .message("Doctor eliminado correctamente")
                        .entity(find)
                        .build());
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Doctor no existe")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al eliminar")
                    .build());
        }
    }

    @PostMapping("doctor/update/{id}")
    public ResponseEntity<BaseResponse> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        try {
            Doctor find = service.getFindUncle(id);
            if (find != null) {
                // Actualiza los campos relevantes (ejemplo simple)
                find.setName(doctor.getName());
                find.setLastname(doctor.getLastname());
                find.setSpecialty(doctor.getSpecialty());
                Doctor updated = service.createOrUpdate(find);

                return ResponseEntity.ok(BaseResponse.builder()
                        .code("200")
                        .message("Doctor actualizado correctamente")
                        .entity(updated)
                        .build());
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Doctor no existe")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al actualizar")
                    .build());
        }
    }

    @PostMapping("doctor")
    public ResponseEntity<BaseResponse> createDoctor(@RequestBody Doctor doctor) {
        try {
            Doctor created = service.createOrUpdate(doctor);
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Doctor creado correctamente")
                    .entity(created)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al crear doctor")
                    .build());
        }
    }
}
