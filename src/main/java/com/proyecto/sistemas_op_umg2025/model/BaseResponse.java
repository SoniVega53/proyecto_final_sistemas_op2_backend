package com.proyecto.sistemas_op_umg2025.model;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private String message;
    private T entidad;
    
    public BaseResponse() {
        
    }

    public BaseResponse(T entidad) {
        this.entidad = entidad;
    }

    public BaseResponse(String message) {
        this.message = message;
    }
}

