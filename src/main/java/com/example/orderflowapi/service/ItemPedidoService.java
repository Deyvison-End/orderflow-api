package com.example.orderflowapi.service;

import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.model.ItemPedido;
import com.example.orderflowapi.model.Produto;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.ItemPedidoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoService produtoService;
    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository,ProdutoService produtoService ){
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoService = produtoService;
    }

    public void validarProduto(ItemPedido itemPedido){
     Integer produtoId = itemPedido.getProduto().getProdutoId();
     produtoService.buscarPorId(produtoId).orElseThrow(() -> new ResourceNotFoundException(
                "Produto não existe"
        ));

    }
    public BigDecimal calcularSubTotal(ItemPedido itemPedido){
        BigDecimal quantidade = BigDecimal.valueOf(itemPedido.getQuantidade());
        BigDecimal precoUnitario = itemPedido.getPrecoUnitario();
        BigDecimal subTotal = quantidade.multiply(precoUnitario);
        itemPedido.setSubtotal(subTotal);
        return subTotal;
    }

    public List<ItemPedido> listarTodos(){
        return itemPedidoRepository.findAll();
    }
    public Optional<ItemPedido> buscarPorID(Integer id){
        return itemPedidoRepository.findById(id);
    }

    public ItemPedido Cadastrar(ItemPedido itemPedido){
        return itemPedidoRepository.save(itemPedido);
    }
    public void excluir(Integer id){
        itemPedidoRepository.deleteById(id);
    }
    public ItemPedido atualizar(Integer id,ItemPedido itemPedido){

        itemPedido.setItemPedidoId(id);

        return itemPedidoRepository.save(itemPedido);
    }
}
