package com.example.orderflowapi.service;

import com.example.orderflowapi.dto.request.ProdutoRequest;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.model.Categoria;
import com.example.orderflowapi.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.ProdutoRepository;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;


@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public  ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService){
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    public Page<Produto> listarTodos(Pageable pageable){
        return produtoRepository.findAll(pageable);
    }

    public Optional<Produto> buscarPorId(Integer id){
        return produtoRepository.findById(id);
    }

    public Produto cadastrar(Produto produto) {
        Integer categoriaId = produto.getCategoria()
                .getCategoriaId();
        categoriaService.buscarPorId(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria não encontrada."
                ));
        return produtoRepository.save(produto);
    }

    public void excluir(Integer id){

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado."
                        ));
        produto.setAtivo(false);

        produtoRepository.save(produto);

    }
    public Produto atualizar(Integer id, ProdutoRequest request) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado."
                        ));

        Categoria categoria = categoriaService.buscarPorId(
                request.getCategoriaId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Categoria não encontrada."
                ));

        produto.setNome(request.getNome());
        produto.setPreco(request.getPreco());
        produto.setQuantidadeEstoque(request.getQuantidadeEstoque());
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public Page<Produto> buscarComFiltros(
            String nome,
            Integer categoriaId,
            BigDecimal precoMin,
            BigDecimal precoMax,
            Pageable pageable) {

        Specification<Produto> specification = (root, query, criteriaBuilder) ->
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

        if (categoriaId != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(
                                    root.get("categoria").get("categoriaId"),
                                    categoriaId
                            )
            );
        }

        if (precoMin != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(
                                    root.get("preco"),
                                    precoMin
                            )
            );
        }

        if (precoMax != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(
                                    root.get("preco"),
                                    precoMax
                            )
            );
        }

        return produtoRepository.findAll(specification, pageable);
    }
}
