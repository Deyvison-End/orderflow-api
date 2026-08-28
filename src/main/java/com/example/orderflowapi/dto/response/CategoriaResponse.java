package com.example.orderflowapi.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaResponse {

    private Integer categoriaId;
    private String nome;
    private String descricao;
}
