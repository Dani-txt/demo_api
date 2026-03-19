package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/v1/users/me - Obtener perfil del usuario logueado
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal String firebaseUid) {
        return userService.findById(firebaseUid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/users/sync - Sincronizar después de login en Firebase
    // El frontend llama esto después de obtener el token de Firebase
    @PostMapping("/sync")
    public ResponseEntity<User> syncUser(@AuthenticationPrincipal String firebaseUid,
                                        @RequestBody Map<String, String> userData) {
        String email = userData.get("email");
        String name = userData.get("name");
        
        User user = userService.syncUser(firebaseUid, email, name);
        return ResponseEntity.ok(user);
    }

    // PUT /api/v1/users/me - Actualizar perfil
    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(@AuthenticationPrincipal String firebaseUid, @RequestBody Map<String, String> updates) {
        String newName = updates.get("name");
        return userService.updateProfile(firebaseUid, newName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
