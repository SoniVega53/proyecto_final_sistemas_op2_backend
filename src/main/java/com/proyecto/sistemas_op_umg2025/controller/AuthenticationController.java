package com.proyecto.sistemas_op_umg2025.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proyecto.sistemas_op_umg2025.model.auth.LoginRequest;
import com.proyecto.sistemas_op_umg2025.model.auth.RegisterRequest;
import com.proyecto.sistemas_op_umg2025.model.entity.BaseResponse;
import com.proyecto.sistemas_op_umg2025.model.entity.User;
import com.proyecto.sistemas_op_umg2025.security.PasswordEncryptor;
import com.proyecto.sistemas_op_umg2025.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proyecto/auth")
@SuppressWarnings("rawtypes")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService service;
    @Value("${APP_NAME}")
    private String appName;

    @PostMapping("register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest entity) {
        try {
            User userCreate = service.register(entity);
            if (userCreate != null) {
                return ResponseEntity.ok(BaseResponse.builder()
                        .code("200")
                        .message("Se creo correctamente")
                        .entity(userCreate).build());
            }
            return ResponseEntity.ok(BaseResponse.builder().code("400").message("Por favor revise sus datos").build());
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.builder().code("400").message("Este usuario ya existe").build());
        }
    }

    @PostMapping("login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = service.getFindUncle(request.getUsername());

            if (user != null) {
                if (checkPassword(request.getPassword(), user.getPassword())) {
                    return ResponseEntity
                            .ok(BaseResponse.builder().code("200").message("Se Inicio Sesion Correctamente")
                                    .entity(user).build());
                } else {
                    return ResponseEntity
                            .ok(BaseResponse.builder().code("400").message("Contraseña Incorrecta, Porfavor verifique.")
                                    .entity(user).build());
                }
            }
            return ResponseEntity.ok(BaseResponse.builder().code("400").message("Este Usuario No existe")
                    .entity(null).build());
        } catch (Exception e) {
            return ResponseEntity.ok(
                    BaseResponse.builder().code("400").message("Usuario no Existe o Contraseña es invalida").build());
        }
    }

    @GetMapping("usuario")
    public String usuarioUMG() {
        return appName;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        try {
            String decrypted = PasswordEncryptor.decrypt(encodedPassword);
            return rawPassword.equals(decrypted);
        } catch (Exception e) {
            return false;
        }
    }
}
