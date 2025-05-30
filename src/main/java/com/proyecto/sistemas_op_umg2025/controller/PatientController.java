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
import com.proyecto.sistemas_op_umg2025.model.entity.Patient;
import com.proyecto.sistemas_op_umg2025.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/proyecto/")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @GetMapping("admin/patients/see")
    public List<Patient> getDataList() {
        return service.getDataList();
    }

    @DeleteMapping("patient/eliminar/{id}")
    public ResponseEntity<BaseResponse> deletePatient(@PathVariable Long id) {
        try {
            Patient find = service.getFindUncle(id);
            if (find != null) {
                service.deleteFind(find);
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("200")
                        .message("Paciente eliminado correctamente")
                        .entity(find)
                        .build());
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Paciente no existe")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al eliminar paciente")
                    .build());
        }
    }

    @PostMapping("patient/update/{id}")
    public ResponseEntity<BaseResponse> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        try {
            Patient find = service.getFindUncle(id);
            if (find != null) {
                find.setName(patient.getName());
                find.setLastname(patient.getLastname());
                find.setPhone(patient.getPhone());
                Patient updated = service.createOrUpdate(find);

                return ResponseEntity.ok(BaseResponse.builder()
                        .code("200")
                        .message("Paciente actualizado correctamente")
                        .entity(updated)
                        .build());
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Paciente no existe")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al actualizar paciente")
                    .build());
        }
    }

    @PostMapping("patient")
    public ResponseEntity<BaseResponse> createPatient(@RequestBody Patient patient) {
        try {
            Patient created = service.createOrUpdate(patient);
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Paciente creado correctamente")
                    .entity(created)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al crear paciente")
                    .build());
        }
    }
}

