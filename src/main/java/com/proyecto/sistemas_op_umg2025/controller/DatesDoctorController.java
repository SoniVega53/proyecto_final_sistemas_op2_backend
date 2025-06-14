package com.proyecto.sistemas_op_umg2025.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.sistemas_op_umg2025.model.entity.BaseResponse;
import com.proyecto.sistemas_op_umg2025.model.entity.DatesDoctor;
import com.proyecto.sistemas_op_umg2025.model.entity.Doctor;
import com.proyecto.sistemas_op_umg2025.model.request.DatesDoctorRequest;
import com.proyecto.sistemas_op_umg2025.service.DatesDoctorService;
import com.proyecto.sistemas_op_umg2025.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proyecto/")
@RequiredArgsConstructor
public class DatesDoctorController {

    private final DatesDoctorService datesDoctorService;
    private final DoctorService doctorService;

    @GetMapping("datesdoctor/see")
    public List<DatesDoctor> getAll() {
        return datesDoctorService.getDataList();
    }

    @GetMapping("datesdoctor/see/doctor/{id_doc}")
    public List<DatesDoctor> getByDoctor(@PathVariable Long id_doc) {
        return datesDoctorService.getByDoctorId(id_doc);
    }

    @PostMapping("datesdoctor")
    public ResponseEntity<BaseResponse> createDateDoctor(@RequestBody DatesDoctorRequest request) {
        try {
            Doctor doctor = doctorService.getFindUncle(request.getDoctorId());
            if (doctor == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Doctor no existe")
                        .build());
            }

            DatesDoctor newDate = DatesDoctor.builder()
                    .doctor(doctor)
                    .date(request.getDate())
                    .build();

            DatesDoctor created = datesDoctorService.createOrUpdate(newDate);

            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Horario creado correctamente")
                    .entity(created)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al crear horario")
                    .build());
        }
    }

    @PostMapping("datesdoctor/update/{id}")
    public ResponseEntity<BaseResponse> updateDateDoctor(@PathVariable Long id,
                                                         @RequestBody DatesDoctorRequest request) {
        try {
            DatesDoctor found = datesDoctorService.getFindUncle(id);
            if (found == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Horario no existe")
                        .build());
            }

            Doctor doctor = doctorService.getFindUncle(request.getDoctorId());
            if (doctor == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Doctor no existe")
                        .build());
            }

            found.setDoctor(doctor);
            found.setDate(request.getDate());

            DatesDoctor updated = datesDoctorService.createOrUpdate(found);

            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Horario actualizado correctamente")
                    .entity(updated)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al actualizar horario")
                    .build());
        }
    }

    @DeleteMapping("datesdoctor/eliminar/{id}")
    public ResponseEntity<BaseResponse> deleteDateDoctor(@PathVariable Long id) {
        try {
            DatesDoctor found = datesDoctorService.getFindUncle(id);
            if (found != null) {
                datesDoctorService.deleteFind(found);
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("200")
                        .message("Horario eliminado correctamente")
                        .entity(found)
                        .build());
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Horario no existe")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al eliminar horario")
                    .build());
        }
    }
}
