package com.example.orderflowapi.service;


import com.example.orderflowapi.dto.request.ClienteRequest;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.mapper.ClienteMapper;
import com.example.orderflowapi.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.ClienteRepository;

import java.time.LocalDate;
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

        public Page<Cliente> buscarComFiltros(
                String nome,
                String cpf,
                String email,
                Pageable pageable) {

            Specification<Cliente> specification =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.isTrue(root.get("ativo"));

                    if (nome != null && !nome.isBlank()) {

                        specification = specification.and(
                                (root, query, criteriaBuilder) ->
                                        criteriaBuilder.like(
                                                criteriaBuilder.lower(root.get("nome")),
                                                "%" + nome.toLowerCase() + "%"
                                        )
                        );
                    }
                    if (cpf != null && !cpf.isBlank()) {

                        specification = specification.and(
                                (root, query, criteriaBuilder) ->
                                        criteriaBuilder.like(
                                                root.get("cpf"),
                                                "%" + cpf + "%"
                                        )
                        );
                    }
                    if (email != null && !email.isBlank()) {

                        specification = specification.and(
                                (root, query, criteriaBuilder) ->
                                        criteriaBuilder.like(
                                                criteriaBuilder.lower(root.get("email")),
                                                "%" + email.toLowerCase() + "%"
                                        )
                        );
                    }
                return clienteRepository.findAll(
                        specification,
                        pageable
                );
    }

    public Optional<Cliente> buscarPorId(Integer id){
        return clienteRepository.findById(id)
                .filter(Cliente::getAtivo);
    }

    public Cliente cadastrar(ClienteRequest request) {
        Cliente cliente = clienteMapper.toEntity(request);
        cliente.setDataCadastro(LocalDate.now());
        return clienteRepository.save(cliente);
    }

    public void excluir(Integer id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente não encontrado."
                        ));

        cliente.setAtivo(false);

        clienteRepository.save(cliente);
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
