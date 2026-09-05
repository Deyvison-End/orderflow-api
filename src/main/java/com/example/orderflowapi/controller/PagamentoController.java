package com.example.orderflowapi.controller;


import com.example.orderflowapi.dto.request.PagamentoRequest;
import com.example.orderflowapi.dto.response.PagamentoResponse;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.mapper.PagamentoMapper;
import com.example.orderflowapi.model.Pagamento;
import com.example.orderflowapi.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {
    private final PagamentoService pagamentoService;
    private final PagamentoMapper pagamentoMapper;

    public PagamentoController(PagamentoService pagamentoService, PagamentoMapper pagamentoMapper){
        this.pagamentoService = pagamentoService;
        this.pagamentoMapper = pagamentoMapper;
    }

    @Operation(
            summary = "Lista todos os pagamentos"

    )
    @GetMapping
    public ResponseEntity<Page<PagamentoResponse>> listarTodos(Pageable pageable) {

        Page<Pagamento> pagamentos = pagamentoService.listarTodos(pageable);

        Page<PagamentoResponse> response = pagamentos.map(pagamentoMapper::toResponse);


        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retornar o Pagamento",
            description = "Verificar no banco de dados se possuir o pagamento e retornar pagamento caso exista caso retorna erro."
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id){

        Pagamento pagamento = pagamentoService.buscarPorId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pagamento não encontrado."
                        ));

        PagamentoResponse response = pagamentoMapper.toResponse(pagamento);

        return ResponseEntity.ok(response);
    }

}
