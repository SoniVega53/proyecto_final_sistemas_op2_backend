package com.proyecto.sistemas_op_umg2025.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequest {
    private Long appointmentId;
    private String medication;
    private String dosage;

}
