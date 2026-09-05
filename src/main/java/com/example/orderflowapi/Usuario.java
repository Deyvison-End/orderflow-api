package com.example.orderflowapi;

import com.example.orderflowapi.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioID;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Role role;

}
