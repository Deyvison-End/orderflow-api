package com.example.orderflowapi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer produtoId;
    private String nome;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    @Builder.Default
    private Boolean ativo = true;

}
