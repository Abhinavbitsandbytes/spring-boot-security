package com.example.productioReady.productioReady.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    // moved from SecurityConfig to avoid circular dependency
    // jwtAuthFilter -> AuthService -> PasswordEncoder -> SecurityConfig -> jwtAuthFilter
    // circular dependency resolved
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
