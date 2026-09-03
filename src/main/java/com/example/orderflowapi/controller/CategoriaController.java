package com.example.orderflowapi.controller;

import com.example.orderflowapi.dto.request.CategoriaRequest;
import com.example.orderflowapi.dto.response.CategoriaResponse;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.mapper.CategoriaMapper;
import com.example.orderflowapi.model.Categoria;
import com.example.orderflowapi.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper){
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }
    @Operation(
            summary = "Listar categorias"

    )
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarTodas() {

        List<Categoria> categorias = categoriaService.listarTodos();

        List<CategoriaResponse> response = categorias.stream()
                .map(categoriaMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retornar uma categoria"

    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable("id") Integer id){

        Categoria categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada."
                        ));

        CategoriaResponse response = categoriaMapper.toResponse(categoria);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Criar uma categoria"

    )
    @ApiResponse(
            responseCode = "201",
            description = "Categoria criada com sucesso"
    )
    @PostMapping
    public ResponseEntity<CategoriaResponse> cadastrar( @Valid @RequestBody CategoriaRequest request){

        Categoria categoriaSalva = categoriaService.cadastrar(request);
        CategoriaResponse response = categoriaMapper.toResponse(categoriaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualizar uma categoria"

    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(@PathVariable("id") Integer id,  @Valid @RequestBody CategoriaRequest request){

        Categoria categoriaSalva = categoriaService.atualizar(id, request);
        CategoriaResponse response =
                categoriaMapper.toResponse(categoriaSalva);

        return ResponseEntity.ok(response);

    }
    @Operation(
            summary = "Deleta uma categoria"

    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deletar(@PathVariable("id") Integer id){

        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
