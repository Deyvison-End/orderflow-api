package com.example.orderflowapi.mapper;

import com.example.orderflowapi.dto.request.CategoriaRequest;
import com.example.orderflowapi.dto.response.CategoriaResponse;
import com.example.orderflowapi.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequest request) {

        return Categoria.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .build();
    }

    public CategoriaResponse toResponse(Categoria categoria) {

        return CategoriaResponse.builder()
                .categoriaId(categoria.getCategoriaId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .build();
    }

}