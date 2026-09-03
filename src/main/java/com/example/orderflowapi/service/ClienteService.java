package com.example.orderflowapi.service;


import com.example.orderflowapi.dto.request.ClienteRequest;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.mapper.ClienteMapper;
import com.example.orderflowapi.model.Cliente;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.ClienteRepository;
import java.util.Optional;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    public ClienteService (ClienteRepository clienteRepository,
                           ClienteMapper clienteMapper){
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Integer id){
        return clienteRepository.findById(id);
    }

    public Cliente cadastrar(ClienteRequest request) {
        Cliente cliente = clienteMapper.toEntity(request);
        return clienteRepository.save(cliente);
    }

    public void excluir(Integer id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente não encontrado."
                        ));

        clienteRepository.delete(cliente);
    }

    public Cliente atualizar(Integer id, ClienteRequest request) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente não encontrado."
                        ));

        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());

        return clienteRepository.save(cliente);
    }
}
