package com.example.orderflowapi.service;

import com.example.orderflowapi.model.Categoria;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.CategoriaRepository;


import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService (CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Integer id){
        return categoriaRepository.findById(id);
    }

    public Categoria cadastrar(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public void excluir(Integer id){
        categoriaRepository.deleteById(id);
        }

    public Categoria atualizar(Integer id, Categoria categoria) {

        categoria.setCategoriaId(id);

        return categoriaRepository.save(categoria);
    }

}
