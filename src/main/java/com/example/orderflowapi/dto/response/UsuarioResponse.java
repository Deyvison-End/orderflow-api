package com.example.orderflowapi.dto.response;

import com.example.orderflowapi.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Integer usuarioId;
    private String email;
    private Role role;
}