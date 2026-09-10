package com.example.orderflowapi.controller;

import com.example.orderflowapi.dto.request.LoginRequest;
import com.example.orderflowapi.dto.request.UsuarioRequest;
import com.example.orderflowapi.dto.response.LoginResponse;
import com.example.orderflowapi.dto.response.UsuarioResponse;
import com.example.orderflowapi.mapper.UsuarioMapper;
import com.example.orderflowapi.model.Usuario;
import com.example.orderflowapi.security.AuthService;
import com.example.orderflowapi.security.JwtService;
import com.example.orderflowapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public AuthController(
            AuthService authService,
            JwtService jwtService,
            UsuarioService usuarioService,
            UsuarioMapper usuarioMapper) {

        this.authService = authService;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(
            @Valid @RequestBody UsuarioRequest request) {

        Usuario usuario = usuarioMapper.toEntity(request);

        Usuario usuarioSalvo =
                usuarioService.cadastrar(usuario);

        UsuarioResponse response =
                usuarioMapper.toResponse(usuarioSalvo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
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