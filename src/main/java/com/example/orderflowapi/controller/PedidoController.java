package com.example.orderflowapi.controller;

import com.example.orderflowapi.dto.request.PedidoRequest;
import com.example.orderflowapi.dto.response.PedidoResponse;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.facade.PedidoFacade;
import com.example.orderflowapi.mapper.PedidoMapper;
import com.example.orderflowapi.model.Pedido;
import com.example.orderflowapi.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;
    private final PedidoFacade pedidoFacade;

    public PedidoController(
            PedidoService pedidoService,
            PedidoFacade pedidoFacade,
            PedidoMapper pedidoMapper) {

        this.pedidoService = pedidoService;
        this.pedidoFacade = pedidoFacade;
        this.pedidoMapper = pedidoMapper;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarTodos() {

        List<PedidoResponse> response = pedidoService.listarTodos()
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(
            @PathVariable Integer id) {

        Pedido pedido = pedidoService.buscarPorId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pedido não encontrado."
                        ));

        return ResponseEntity.ok(
                pedidoMapper.toResponse(pedido)
        );
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(
            @Valid @RequestBody PedidoRequest request) {

        Pedido pedido = pedidoFacade.processarPedido(request);

        PedidoResponse response =
                pedidoMapper.toResponse(pedido);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id) {

        pedidoService.buscarPorId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pedido não encontrado."
                        ));

        pedidoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}