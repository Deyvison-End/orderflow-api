package com.example.orderflowapi.mapper;


import com.example.orderflowapi.dto.request.ClienteRequest;
import com.example.orderflowapi.dto.response.ClienteResponse;
import com.example.orderflowapi.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request){

        return Cliente.builder()
                .nome(request.getNome())
                .cpf(request.getCpf())
                .email(request.getEmail())
                .telefone(request.getTelefone())
                .dataCadastro(request.getDataCadastro())
                .build();
    }

    public ClienteResponse toResponse(Cliente cliente){

        return ClienteResponse.builder()
                .clienteId(cliente.getClienteId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .telefone(cliente.getTelefone())
                .dataCadastro(cliente.getDataCadastro())
                .build();
    }
}
