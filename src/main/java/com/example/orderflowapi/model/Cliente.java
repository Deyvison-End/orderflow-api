package com.example.orderflowapi.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clienteId;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDate dataCadastro;

}
