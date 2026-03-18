package com.example.demo.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccountStream = getCredentialsStream();
                
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .build();

                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase inicializado correctamente");
            }
        } catch (IOException e) {
            throw new RuntimeException("❌ Error al inicializar Firebase: " + e.getMessage(), e);
        }
    }

    private InputStream getCredentialsStream() throws IOException {
        // Opción 1: Variable de entorno (Render/Producción)
        String firebaseConfig = System.getenv("FIREBASE_CONFIG");
        
        if (firebaseConfig != null && !firebaseConfig.isEmpty()) {
            System.out.println("🔧 Usando Firebase desde variable de entorno");
            return new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));
        }
        
        // Opción 2: Archivo local (Desarrollo)
        ClassPathResource resource = new ClassPathResource("fir-api-9786d-firebase-adminsdk-fbsvc-b78fe255cc.json");
        if (resource.exists()) {
            System.out.println("🔧 Usando Firebase desde archivo local");
            return resource.getInputStream();
        }
        
        throw new RuntimeException(
            "No se encontró configuración de Firebase. " +
            "Debe configurar FIREBASE_CONFIG como variable de entorno " +
            "o colocar firebase-service-account.json en src/main/resources"
        );
    }
}