package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestAuthController {

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("Endpoint público - no necesitas token");
    }

    @GetMapping("/private")
    public ResponseEntity<Map<String, String>> privateEndpoint(
            @AuthenticationPrincipal String firebaseUid) {
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Autenticación exitosa");
        response.put("firebaseUid", firebaseUid);
        response.put("timestamp", LocalDateTime.now().toString());
        
        return ResponseEntity.ok(response);
    }
}