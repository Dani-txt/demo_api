package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Password encoder pa cuando lo use
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Públicos: Swagger, health, login
                .requestMatchers(
                    "/doc/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/actuator/**",
                    "/login",
                    "/error"
                ).permitAll()
                //La idea es ir haciendo publicos los endpoint de a poco
                //cualquier otra cosa que no esté arriba solicitará auntenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.permitAll());
        
        return http.build();
    }
}