package com.example.orderflowapi.service;


import com.example.orderflowapi.model.Cliente;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.ClienteRepository;
import java.util.Optional;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService (ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Integer id){
        return clienteRepository.findById(id);
    }

    public Cliente cadastrar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void excluir(Integer id){
        clienteRepository.deleteById(id);
    }

    public Cliente atualizar(Integer id,Cliente cliente){
        cliente.setClienteId(id);

        return clienteRepository.save(cliente);
    }
}
