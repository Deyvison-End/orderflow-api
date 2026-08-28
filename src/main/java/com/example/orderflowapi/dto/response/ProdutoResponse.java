package com.example.orderflowapi.dto.response;

import com.example.orderflowapi.model.Categoria;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoResponse {
    private Integer produtoId;
    private String nome;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private Integer categoriaId;
    private String categoriaNome;
}
