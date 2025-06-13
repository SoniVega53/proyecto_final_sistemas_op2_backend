package com.proyecto.sistemas_op_umg2025.controller;

import com.proyecto.sistemas_op_umg2025.model.entity.BaseResponse;
import com.proyecto.sistemas_op_umg2025.model.entity.Specialty;
import com.proyecto.sistemas_op_umg2025.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyecto/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping("/see")
    public List<Specialty> getAllSpecialties() {
        return specialtyService.getDataList();
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createSpecialty(@RequestBody Specialty specialty) {
        Specialty created = specialtyService.createOrUpdate(specialty);
        return ResponseEntity.ok(BaseResponse.builder()
                .code("200")
                .message("Especialidad creada correctamente")
                .entity(created)
                .build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse> updateSpecialty(@PathVariable Long id, @RequestBody Specialty updatedData) {
        updatedData.setId(id);
        Specialty updated = specialtyService.createOrUpdate(updatedData);
        return ResponseEntity.ok(BaseResponse.builder()
                .code("200")
                .message("Especialidad actualizada correctamente")
                .entity(updated)
                .build());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<BaseResponse> deleteSpecialty(@PathVariable Long id) {
        try {
            Specialty data = specialtyService.getFindUncle(id);
            specialtyService.deleteFind(data);
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Especialidad eliminada correctamente")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado")
                    .entity(e)
                    .build());
        }
    }
}
