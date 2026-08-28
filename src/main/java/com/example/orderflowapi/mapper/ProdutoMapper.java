package com.example.orderflowapi.mapper;

import com.example.orderflowapi.dto.request.ProdutoRequest;
import com.example.orderflowapi.dto.response.ProdutoResponse;
import com.example.orderflowapi.model.Categoria;
import com.example.orderflowapi.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest request){

        Categoria categoria = Categoria.builder()
                .categoriaId(request.getCategoriaId())
                .build();

        return Produto.builder()
                .nome(request.getNome())
                .preco(request.getPreco())
                .quantidadeEstoque(request.getQuantidadeEstoque())
                .categoria(categoria)
                .build();
    }

    public ProdutoResponse toResponse(Produto produto){

        return ProdutoResponse.builder()
                .produtoId(produto.getProdutoId())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .categoriaId(produto.getCategoria().getCategoriaId())
                .categoriaNome(produto.getCategoria().getNome())
                .quantidadeEstoque(produto.getQuantidadeEstoque())
                .build();
    }
}
