package com.example.orderflowapi.dto.request;

import com.example.orderflowapi.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoRequest {

    @NotBlank(message = "Informe o nome do produto")
    private String nome;
    @Positive(message = "Informe um valor válido")
    @NotNull(message = "É necessário informar o valor")
    private BigDecimal preco;
    @PositiveOrZero(message = "Informe o valor válido")
    @NotNull(message = "Qual a quantidade no estoque")
    private Integer quantidadeEstoque;
    @NotNull(message = "Informe qual é categoria do produto")
    private Integer categoriaId;
}
