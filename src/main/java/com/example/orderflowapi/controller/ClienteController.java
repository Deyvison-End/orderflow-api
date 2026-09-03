package com.example.orderflowapi.controller;

import com.example.orderflowapi.dto.request.ClienteRequest;
import com.example.orderflowapi.dto.response.ClienteResponse;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.mapper.ClienteMapper;
import com.example.orderflowapi.model.Cliente;
import com.example.orderflowapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper){
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    @Operation(
            summary = "Retornar os clientes"

    )
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarTodos() {

        List<Cliente> clientes = clienteService.listarTodos();

        List<ClienteResponse> response = clientes.stream()
                .map(clienteMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retornar um cliente"

    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id){

        Cliente cliente = clienteService.buscarPorId(id).get();

        ClienteResponse response = clienteMapper.toResponse(cliente);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Criar cliente",
            description = "Cria um novo cliente e processa seu pagamento."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Cliente criado com sucesso"
    )
    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar( @Valid @RequestBody ClienteRequest request){

        Cliente Cliente = clienteMapper.toEntity(request);
        Cliente ClienteSalva = clienteService.cadastrar(Cliente);
        ClienteResponse response = clienteMapper.toResponse(ClienteSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualizar um cliente"

    )
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Integer id, @Valid @RequestBody ClienteRequest request){

        clienteService.buscarPorId(id).orElseThrow(() ->
                new ResourceNotFoundException("Cliente não encontrada."));
        Cliente cliente = clienteMapper.toEntity(request);
        cliente.setClienteId(id);
        Cliente clienteSalva = clienteService.atualizar(id, cliente);
        ClienteResponse response =
                clienteMapper.toResponse(clienteSalva);

        return ResponseEntity.ok(response);

    }

    @Operation(
            summary = "Deleta um cliente"

    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deletar(@PathVariable("id") Integer id){
        clienteService.buscarPorId(id).orElseThrow(() ->
                new ResourceNotFoundException("Cliente não encontrada."));
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
