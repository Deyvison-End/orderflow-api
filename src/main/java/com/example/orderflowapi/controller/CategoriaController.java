package com.example.orderflowapi.controller;

import com.example.orderflowapi.dto.request.CategoriaRequest;
import com.example.orderflowapi.dto.response.CategoriaResponse;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.mapper.CategoriaMapper;
import com.example.orderflowapi.model.Categoria;
import com.example.orderflowapi.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.repository.util.ReactiveWrapperConverters.map;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper){
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarTodas() {

        List<Categoria> categorias = categoriaService.listarTodos();

        List<CategoriaResponse> response = categorias.stream()
                .map(categoriaMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id){

        Categoria categoria = categoriaService.buscarPorId(id).get();

        CategoriaResponse response = categoriaMapper.toResponse(categoria);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> cadastrar( @Valid @RequestBody CategoriaRequest request){

        Categoria categoria = categoriaMapper.toEntity(request);
        Categoria categoriaSalva = categoriaService.cadastrar(categoria);
        CategoriaResponse response = categoriaMapper.toResponse(categoriaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Integer id,  @Valid @RequestBody CategoriaRequest request){

        categoriaService.buscarPorId(id).orElseThrow(() ->
                        new ResourceNotFoundException("Categoria não encontrada."));
        Categoria categoria = categoriaMapper.toEntity(request);
        categoria.setCategoriaId(id);
        Categoria categoriaSalva = categoriaService.atualizar(id, categoria);
        CategoriaResponse response =
                categoriaMapper.toResponse(categoriaSalva);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deletar(@PathVariable("id") Integer id){
        categoriaService.buscarPorId(id).orElseThrow(() ->
                new ResourceNotFoundException("Categoria não encontrada."));
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
