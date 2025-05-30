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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.sistemas_op_umg2025.model.auth.RegisterRequest;
import com.proyecto.sistemas_op_umg2025.model.entity.BaseResponse;
import com.proyecto.sistemas_op_umg2025.model.entity.User;
import com.proyecto.sistemas_op_umg2025.security.PasswordEncryptor;
import com.proyecto.sistemas_op_umg2025.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/proyecto/")
@SuppressWarnings("rawtypes")
@RequiredArgsConstructor

public class UserController {

    private final UserService service;

    @GetMapping("admin/usuario/see_all")
    public List<User> getDataList() {
        return service.getDataList();
    }

    @DeleteMapping("usuario/eliminar/{id}")
    public ResponseEntity<BaseResponse> deleteUsuario(@PathVariable Long id) {
        try {
            User find = service.getFindUncle(id);
            if (find != null) {
                service.deleteFind(find);
                return ResponseEntity.ok(BaseResponse.builder().code("200").message("Se elimino Correctamente")
                        .entity(find).build());
            }
            return ResponseEntity.ok(
                    BaseResponse.builder().code("400").message("Usuario no Existe o Contraseña es invalida").build());
        } catch (Exception e) {

            return ResponseEntity.ok(
                    BaseResponse.builder().code("400")
                            .message("Surgio Algo Inesperado, Revise que no tenga articulos creados").build());
        }
    }

    @PostMapping("usuario/update/{id}")
    public ResponseEntity<BaseResponse> updateUsuario(@PathVariable Long id, @RequestBody RegisterRequest user) {
        try {
            User find = service.getFindUncle(id);
            if (find != null) {
                service.updateUser(user, find);
                return ResponseEntity.ok(BaseResponse.builder().code("200").message("Se actualizo Correctamente")
                        .entity(find).build());
            }
            return ResponseEntity.ok(
                    BaseResponse.builder().code("400").message("Usuario no Existe o Contraseña es invalida").build());
        } catch (Exception e) {
            return ResponseEntity.ok(
                    BaseResponse.builder().code("400").message("Surgio Algo Inesperado").build());
        }
    }

    @PostMapping("usuario/cambiarPassword")
    public ResponseEntity<BaseResponse> updateUsuarioPassword(@RequestBody RegisterRequest user) {
        try {
            User find = service.getFindUncle(user.getUsername());
            if (find != null) {
                if (user.getPassword() != null && user.getPasswordChange() != null &&
                        !user.getPassword().isEmpty() && !user.getPasswordChange().isEmpty()) {

                    if (checkPassword(user.getPassword(), find.getPassword())) {
                        String encrypted = PasswordEncryptor.encrypt(user.getPasswordChange());
                        user.setPasswordChange(encrypted);

                        service.changePassword(user, find);

                        return ResponseEntity
                                .ok(BaseResponse.builder().code("200").message("Se actualizo Correctamente")
                                        .entity(find).build());
                    } else {
                        return ResponseEntity.ok(
                                BaseResponse.builder().code("400").message("Contraseña no coincide con la actual").build());
                    }
                }
                return ResponseEntity.ok(
                        BaseResponse.builder().code("400").message("Contraseña no es valida").build());
            }
            return ResponseEntity.ok(
                    BaseResponse.builder().code("400").message("Usuario no Existe").build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    BaseResponse.builder().code("400").message("Surgio Algo Inesperado").build());
        }
    }

    @PostMapping("user/usuario")
    public ResponseEntity<BaseResponse> login(@RequestParam String usuario) {
        try {
            return ResponseEntity.ok(BaseResponse.builder().code("200").message("Inicio Correctamente")
                    .entity(service.obtenerUser(usuario)).build());
        } catch (Exception e) {
            return ResponseEntity.ok(
                    BaseResponse.builder().code("400").message("Usuario no Existe o Contraseña es invalida").build());

        }
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