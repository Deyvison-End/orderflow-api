package com.example.orderflowapi.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {

    private Integer clienteId;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDate dataCadastro;
}
