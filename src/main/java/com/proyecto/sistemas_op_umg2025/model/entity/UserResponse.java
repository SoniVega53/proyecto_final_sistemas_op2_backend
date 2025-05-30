package com.proyecto.sistemas_op_umg2025.model.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String username;
    String name;
    String lastname;
    String email;
    String rol;
    String specialty;
    String phone;
}
