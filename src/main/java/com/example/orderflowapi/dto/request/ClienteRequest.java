package com.example.orderflowapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O formato de email está inválido")
    private String email;
    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 14, max = 14, message = "CPF deve possuir 11 caracteres.")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "CPF deve estar no formato XXX.XXX.XXX-XX"
    )
    private String cpf;
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    private LocalDate dataCadastro;
}
