package com.example.orderflowapi.service;

import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.model.ItemPedido;
import com.example.orderflowapi.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.ItemPedidoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    private final ProdutoService produtoService;

    public ItemPedidoService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public void validarProduto(ItemPedido itemPedido) {

        Integer produtoId =
                itemPedido.getProduto().getProdutoId();

        produtoService.buscarPorId(produtoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não existe."
                        ));
    }

    public BigDecimal calcularSubTotal(ItemPedido itemPedido) {

        BigDecimal quantidade =
                BigDecimal.valueOf(itemPedido.getQuantidade());

        BigDecimal precoUnitario =
                itemPedido.getPrecoUnitario();

        BigDecimal subtotal =
                quantidade.multiply(precoUnitario);

        itemPedido.setSubtotal(subtotal);

        return subtotal;
    }
}
