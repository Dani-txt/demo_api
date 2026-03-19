package com.example.demo.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//OncePerRequestFilter permite una solicitud por petición http, garantiza base de seguridad
@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        //Se extrae el header del token (JSON) 
        String header = request.getHeader("Authorization");
        
        //Se extrae de la función la segmento del bearer
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            
            //Comienza valiadción
            try {
                //Instancio el token y obtengo el uid y el email
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                String uid = decodedToken.getUid();
                String email = decodedToken.getEmail();
                
                // Crear autenticación de Spring Security
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        uid,                    // Principal (el UID de Firebase)
                        null,                   // Credentials (no necesitamos password)
                        Collections.emptyList() // Authorities (roles los sacaremos de nuestra BD después)
                    );
                
                // Guardar en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            } catch (FirebaseAuthException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido: " + e.getMessage());
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
