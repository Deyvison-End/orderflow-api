package com.example.orderflowapi.controller;

import com.example.orderflowapi.dto.request.LoginRequest;
import com.example.orderflowapi.dto.response.LoginResponse;
import com.example.orderflowapi.security.AuthService;
import com.example.orderflowapi.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService,
            JwtService jwtService) {

        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        Authentication authentication =
                authService.autenticar(
                        request.getEmail(),
                        request.getSenha()
                );

        String token = jwtService.gerarToken(authentication);

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .build();

        return ResponseEntity.ok(response);
    }
}