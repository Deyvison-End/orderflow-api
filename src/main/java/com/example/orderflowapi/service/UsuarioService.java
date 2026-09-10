package com.example.orderflowapi.service;

import com.example.orderflowapi.enums.Role;
import com.example.orderflowapi.model.Usuario;
import com.example.orderflowapi.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario cadastrar(Usuario usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        if (usuario.getRole() == null) {
            usuario.setRole(Role.USER);
        }

        return usuarioRepository.save(usuario);
    }
}