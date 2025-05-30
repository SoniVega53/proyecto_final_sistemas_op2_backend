package com.proyecto.sistemas_op_umg2025;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyecto.sistemas_op_umg2025.model.RoleUser;
import com.proyecto.sistemas_op_umg2025.model.auth.RegisterRequest;
import com.proyecto.sistemas_op_umg2025.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ConfigurationAuth {
    private final UserService service;

    @Bean
    CommandLineRunner init() {
        return args -> {
            /* 
              
            */
           try {
                service.getFindUncle("admin");
            } catch (Exception e) {
                service.registerAdmin(RegisterRequest.builder()
                        .username("admin")
                        .password("admin")
                        .name("admin")
                        .lastname("admin")
                        .rol(RoleUser.ADMIN.name())
                        .email("admin@gmail.com")
                        .build());
            }
        };
    }
       
}
