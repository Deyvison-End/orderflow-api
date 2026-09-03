package com.example.orderflowapi.controller;

import com.example.orderflowapi.dto.request.ProdutoRequest;
import com.example.orderflowapi.dto.response.ProdutoResponse;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.mapper.ProdutoMapper;
import com.example.orderflowapi.model.Produto;
import com.example.orderflowapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper){
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @Operation(
            summary = "Listar os produtos"

    )
    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> listarTodos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            Pageable pageable) {

        Page<Produto> produtos = produtoService.buscarComFiltros(
                nome,
                categoriaId,
                precoMin,
                precoMax,
                pageable
        );

        Page<ProdutoResponse> response =
                produtos.map(produtoMapper::toResponse);


        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Retornar um produto"

    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id){

        Produto produto = produtoService.buscarPorId(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("Produto não encontrado.")
        );

        ProdutoResponse response = produtoMapper.toResponse(produto);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Criar produto",
            description = "Cria um novo produto e processa seu pagamento."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Produto criado com sucesso"
    )
    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar( @Valid @RequestBody ProdutoRequest request){

        Produto produto = produtoMapper.toEntity(request);
        Produto produtoSalva = produtoService.cadastrar(produto);
        ProdutoResponse response = produtoMapper.toResponse(produtoSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualizar um produto"

    )
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProdutoRequest request) {

        Produto produtoSalvo =
                produtoService.atualizar(id, request);

        ProdutoResponse response =
                produtoMapper.toResponse(produtoSalvo);

        return ResponseEntity.ok(response);
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(
                @PathVariable Integer id) {

            produtoService.excluir(id);

            return ResponseEntity.noContent().build();
        }
}
