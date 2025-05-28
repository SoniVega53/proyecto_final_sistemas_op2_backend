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
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String rol;
}
