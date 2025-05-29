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

import com.proyecto.sistemas_op_umg2025.model.entity.Appointment;
import com.proyecto.sistemas_op_umg2025.model.entity.BaseResponse;
import com.proyecto.sistemas_op_umg2025.model.entity.Prescription;
import com.proyecto.sistemas_op_umg2025.model.request.PrescriptionRequest;
import com.proyecto.sistemas_op_umg2025.service.AppointmentService;
import com.proyecto.sistemas_op_umg2025.service.PrescriptionService;

import lombok.RequiredArgsConstructor;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/proyecto/")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService service;
    private final AppointmentService appointmentService;

    @GetMapping("admin/prescriptions/see")
    public List<Prescription> getDataList() {
        return service.getDataList();
    }

    @DeleteMapping("prescription/eliminar/{id}")
    public ResponseEntity<BaseResponse> deletePrescription(@PathVariable Long id) {
        try {
            Prescription find = service.getFindUncle(id);
            if (find != null) {
                service.deleteFind(find);
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("200")
                        .message("Prescripción eliminada correctamente")
                        .entity(find)
                        .build());
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Prescripción no existe")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al eliminar prescripción")
                    .build());
        }
    }

    @PostMapping("prescription/update/{id}")
    public ResponseEntity<BaseResponse> updatePrescription(@PathVariable Long id, @RequestBody PrescriptionRequest request) {
        try {
            Prescription find = service.getFindUncle(id);
            if (find == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Prescripción no existe")
                        .build());
            }

            Appointment appointment = appointmentService.getFindUncle(request.getAppointmentId());
            if (appointment == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Cita no existe")
                        .build());
            }

            find.setAppointment(appointment);
            find.setMedication(request.getMedication());
            find.setDosage(request.getDosage());

            Prescription updated = service.createOrUpdate(find);

            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Prescripción actualizada correctamente")
                    .entity(updated)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al actualizar prescripción")
                    .build());
        }
    }

    @PostMapping("prescription")
    public ResponseEntity<BaseResponse> createPrescription(@RequestBody PrescriptionRequest request) {
        try {
            Appointment appointment = appointmentService.getFindUncle(request.getAppointmentId());
            if (appointment == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Cita no existe")
                        .build());
            }

            Prescription prescription = new Prescription();
            prescription.setAppointment(appointment);
            prescription.setMedication(request.getMedication());
            prescription.setDosage(request.getDosage());

            Prescription created = service.createOrUpdate(prescription);

            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Prescripción creada correctamente")
                    .entity(created)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al crear prescripción")
                    .build());
        }
    }
}

