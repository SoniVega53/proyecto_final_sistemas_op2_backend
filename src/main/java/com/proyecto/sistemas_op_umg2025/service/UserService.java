package com.proyecto.sistemas_op_umg2025.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.sistemas_op_umg2025.model.RoleUser;
import com.proyecto.sistemas_op_umg2025.model.auth.RegisterRequest;
import com.proyecto.sistemas_op_umg2025.model.entity.Doctor;
import com.proyecto.sistemas_op_umg2025.model.entity.Patient;
import com.proyecto.sistemas_op_umg2025.model.entity.User;
import com.proyecto.sistemas_op_umg2025.model.repository.DoctorRepository;
import com.proyecto.sistemas_op_umg2025.model.repository.PatientRepository;
import com.proyecto.sistemas_op_umg2025.model.repository.UserRepository;
import com.proyecto.sistemas_op_umg2025.security.PasswordEncryptor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceCRUD<User> {

    @Autowired
    private UserRepository repository;
    @Autowired
    private DoctorRepository repositoryDoc;
    @Autowired
    private PatientRepository repositoryPat;

    @Override
    public User createOrUpdate(User value) {
        return repository.save(value);
    }

    @Override
    public List<User> getDataList() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User getFindUncle(Long value) {
        Optional<User> res = repository.findById(value);
        return res.isPresent() ? res.get() : null;
    }

    public User getFindUncleUsername(String value) {
        User user = repository.findByUsername(value).orElseThrow();
        return user;
    }

    public User getFindUncle(String value) {
        User user = repository.findByUsername(value).orElseThrow();
        return user;
    }

    @Override
    public void deleteFind(User value) {
        repository.delete(value);
    }

    public User updateUser(RegisterRequest request, User userFind) {

        User user = User.builder()
                .id(userFind.getId())
                .username(userFind.getUsername())
                .password(userFind.getPassword())
                .email(request.getEmail())
                .rol(userFind.getRol())
                .build();
        if (user == null)
            return null;

        if (RoleUser.valueOf(request.getRol()) == RoleUser.DOCTOR) {
            Doctor doctor = Doctor.builder()
                    .id(request.getId_doctor())
                    .name(request.getName())
                    .lastname(request.getLastname())
                    .specialty(request.getSpecialty())
                    .user(user)
                    .build();
            repositoryDoc.save(doctor);
        } else if (RoleUser.valueOf(request.getRol()) == RoleUser.USER) {
            Patient pat = Patient.builder()
                    .id(request.getId_paciente())
                    .name(request.getName())
                    .lastname(request.getLastname())
                    .phone(request.getPhone())
                    .user(user)
                    .build();
            repositoryPat.save(pat);
        }

        return repository.save(user);
    }

    public User changePassword(RegisterRequest userFind, User find) {
        User user = User.builder()
                .id(find.getId())
                .username(find.getUsername())
                .password(userFind.getPasswordChange())
                .rol(find.getRol())
                .email(find.getEmail())
                .build();

        return repository.save(user);
    }

    public User obtenerUser(String usuario) {
        User user = repository.findByUsername(usuario).orElseThrow();
        return user;
    }

    public User register(RegisterRequest request) {
        try {
            String encrypted = PasswordEncryptor.encrypt(request.getPassword());
            User user = User.builder()
                    .username(request.getUsername())
                    .password(encrypted)
                    .rol(request.getRol())
                    .email(request.getEmail())
                    .build();
            user = repository.save(user);
            if (RoleUser.valueOf(request.getRol()) == RoleUser.DOCTOR) {
                Doctor doctor = Doctor.builder()
                        .name(request.getName())
                        .lastname(request.getLastname())
                        .specialty(request.getSpecialty())
                        .user(user)
                        .build();
                repositoryDoc.save(doctor);
            } else if (RoleUser.valueOf(request.getRol()) == RoleUser.USER) {
                Patient pat = Patient.builder()
                        .name(request.getName())
                        .lastname(request.getLastname())
                        .phone(request.getPhone())
                        .user(user)
                        .build();
                repositoryPat.save(pat);
            }

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User registerAdmin(RegisterRequest request) {
        try {
            String encrypted = PasswordEncryptor.encrypt(request.getPassword());
            User user = User.builder()
                    .username(request.getUsername())
                    .password(encrypted)
                    .rol(request.getRol())
                    .email(request.getEmail())
                    .build();

            return repository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
