package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entity.User;
import com.example.demo.model.enums.Role;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Sincroniza usuario de Firebase con nuestra BD local.
     * Si no existe, lo crea. Si existe, actualiza datos básicos.
     */
    public User syncUser(String firebaseUid, String email, String name) {
        return userRepository.findById(firebaseUid)
            .orElseGet(() -> {
                // Crear nuevo usuario
                User newUser = new User();
                newUser.setFirebaseUid(firebaseUid);
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setRole(Role.CLIENT); // Por defecto cliente
                newUser.setCreatedAt(LocalDateTime.now());
                return userRepository.save(newUser);
            });
    }

    // Obtener perfil por UID
    @Transactional(readOnly = true)
    public Optional<User> findById(String firebaseUid) {
        return userRepository.findById(firebaseUid);
    }

    // Actualizar perfil (nombre, etc. - no email ni uid que vienen de Firebase)
    public Optional<User> updateProfile(String firebaseUid, String newName) {
        return userRepository.findById(firebaseUid).map(user -> {
            user.setName(newName);
            return userRepository.save(user);
        });
    }

    // Cambiar rol (solo para admin)
    public User changeRole(String firebaseUid, Role newRole) {
        User user = userRepository.findById(firebaseUid)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setRole(newRole);
        return userRepository.save(user);
    }
}
