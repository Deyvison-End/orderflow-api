package com.example.orderflowapi.mapper;

import com.example.orderflowapi.dto.request.UsuarioRequest;
import com.example.orderflowapi.dto.response.UsuarioResponse;

import com.example.orderflowapi.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {

        return Usuario.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .role(request.getRole())
                .build();
    }

    public UsuarioResponse toResponse(Usuario usuario) {

        return UsuarioResponse.builder()
                .usuarioId(usuario.getUsuarioId())
                .email(usuario.getEmail())
                .role(usuario.getRole())
                .build();
    }
}