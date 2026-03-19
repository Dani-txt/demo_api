package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Inyectamos el filtro del la auth
    private final FirebaseAuthenticationFilter firebaseFilter;

    public SecurityConfig(FirebaseAuthenticationFilter firebaseFilter) {
        this.firebaseFilter = firebaseFilter;
    }

    //Filtro de seguridad para las conexiones (url publicos) con mi api
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(firebaseFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/doc/**",           // Swagger público
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/actuator/**",
                    "/api/v1/health",    // Tu health check
                    "/api/v1/test/public",//Testeo de pipeline firebas
                    "/api/v1/categories", //Busqueda de categoría publica
                    "/api/v1/products", //PRoductos publicos
                    "/error"
                    
                ).permitAll()
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}