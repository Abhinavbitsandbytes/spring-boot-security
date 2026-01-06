package com.example.productioReady.productioReady.services;

import com.example.productioReady.productioReady.dto.LoginDTO;
import com.example.productioReady.productioReady.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return token;
    }
}

// in this service class, we have a method login which takes LoginDTO as input. It uses the AuthenticationManager to authenticate the user based on the provided email and password. If authentication is successful, it generates a JWT token using the JwtService and returns it.
// Now, when you hit the /auth/login endpoint with valid credentials, you will receive a JWT token in response.

