package com.example.productioReady.productioReady.controllers;

import com.example.productioReady.productioReady.dto.SignUpDTO;
import com.example.productioReady.productioReady.dto.UserDTO;
import com.example.productioReady.productioReady.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDto = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDto);

    }
}
