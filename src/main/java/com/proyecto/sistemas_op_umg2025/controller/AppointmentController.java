package com.proyecto.sistemas_op_umg2025.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.sistemas_op_umg2025.model.entity.Appointment;
import com.proyecto.sistemas_op_umg2025.model.entity.BaseResponse;
import com.proyecto.sistemas_op_umg2025.model.entity.Doctor;
import com.proyecto.sistemas_op_umg2025.model.entity.Patient;
import com.proyecto.sistemas_op_umg2025.model.request.AppointmentRequest;
import com.proyecto.sistemas_op_umg2025.service.AppointmentService;
import com.proyecto.sistemas_op_umg2025.service.DoctorService;
import com.proyecto.sistemas_op_umg2025.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proyecto/")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping("appointments/see")
    public List<Appointment> getDataList() {
        return service.getDataList();
    }

    @GetMapping("appointments/see/doctor/{id_doc}")
    public List<Appointment> getDataListDoctor(@PathVariable Long id_doc) {
        return service.getByDoctorId(id_doc);
    }

    @GetMapping("appointments/see/paciente/{id_pac}")
    public List<Appointment> getDataListPaciente(@PathVariable Long id_pac) {
        return service.getByPatientId(id_pac);
    }

    @DeleteMapping("appointment/eliminar/{id}")
    public ResponseEntity<BaseResponse> deleteAppointment(@PathVariable Long id) {
        try {
            Appointment find = service.getFindUncle(id);
            if (find != null) {
                service.deleteFind(find);
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("200")
                        .message("Cita eliminada correctamente")
                        .entity(find)
                        .build());
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Cita no existe")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al eliminar cita")
                    .build());
        }
    }

    @PostMapping("appointment/update/{id}")
    public ResponseEntity<BaseResponse> updateAppointment(@PathVariable Long id,
            @RequestBody AppointmentRequest request) {
        try {
            Appointment find = service.getFindUncle(id);
            if (find == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Cita no existe")
                        .build());
            }

            Doctor doctor = doctorService.getFindUncle(request.getDoctorId());
            Patient patient = patientService.getFindUncle(request.getPatientId());
            if (doctor == null || patient == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Doctor o Paciente no existen")
                        .build());
            }

            find.setDoctor(doctor);
            find.setPatient(patient);
            find.setDate(request.getDate());

            Appointment updated = service.createOrUpdate(find);

            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Cita actualizada correctamente")
                    .entity(updated)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al actualizar cita")
                    .build());
        }
    }

    @PostMapping("appointment")
    public ResponseEntity<BaseResponse> createAppointment(@RequestBody AppointmentRequest request) {
        try {
            Doctor doctor = doctorService.getFindUncle(request.getDoctorId());
            Patient patient = patientService.getFindUncle(request.getPatientId());
            if (doctor == null || patient == null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("400")
                        .message("Doctor o Paciente no existen")
                        .build());
            }

            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setDate(request.getDate());

            Appointment created = service.createOrUpdate(appointment);

            return ResponseEntity.ok(BaseResponse.builder()
                    .code("200")
                    .message("Cita creada correctamente")
                    .entity(created)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .code("400")
                    .message("Error inesperado al crear cita")
                    .build());
        }
    }
}
