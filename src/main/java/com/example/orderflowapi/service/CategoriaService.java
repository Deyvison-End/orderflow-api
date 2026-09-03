package com.example.orderflowapi.service;

import com.example.orderflowapi.dto.request.CategoriaRequest;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.mapper.CategoriaMapper;
import com.example.orderflowapi.model.Categoria;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    public CategoriaService (CategoriaRepository categoriaRepository,
                             CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }


    public Optional<Categoria> buscarPorId(Integer id){
        return categoriaRepository.findById(id);
    }

    public Categoria cadastrar(CategoriaRequest request){
        Categoria categoria = categoriaMapper.toEntity(request);
        return categoriaRepository.save(categoria);
    }

    public void excluir(Integer id){

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada."
                        ));

        categoriaRepository.delete(categoria);
        }

    public Categoria atualizar(Integer id, CategoriaRequest request) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada."
                        ));


        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        return categoriaRepository.save(categoria);
    }

}
