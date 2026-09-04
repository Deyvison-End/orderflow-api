package com.example.orderflowapi.service;

import com.example.orderflowapi.enums.StatusPagamento;
import com.example.orderflowapi.model.*;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.PedidoRepository;
import java.util.List;
import java.util.Optional;
import com.example.orderflowapi.dto.request.ItemPedidoRequest;
import com.example.orderflowapi.dto.request.PedidoRequest;
import com.example.orderflowapi.exception.ResourceNotFoundException;
import com.example.orderflowapi.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final PagamentoService pagamentoService;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteService clienteService,
                    ProdutoService produtoService,
                         PagamentoService pagamentoService
    ){
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.pagamentoService = pagamentoService;
    }

    public Pedido montarPedido(PedidoRequest request) {

        Cliente cliente = clienteService.buscarPorId(request.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente não encontrado."
                        ));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .dataPedido(LocalDateTime.now())
                .statusPedido(StatusPedido.CRIADO)
                .valorTotal(BigDecimal.ZERO)
                .build();


        Pagamento pagamento = pagamentoService.montarPagamento(request.getPagamento());

        pagamento.setPedido(pedido);
        pedido.setPagamento(pagamento);

        for (ItemPedidoRequest itemRequest : request.getItens()) {

            Produto produto = produtoService.buscarPorId(
                    itemRequest.getProdutoId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Produto não encontrado."
                    ));

            BigDecimal preco = produto.getPreco();

            ItemPedido item = ItemPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(itemRequest.getQuantidade())
                    .precoUnitario(preco)
                    .build();

            pedido.getItens().add(item);
        }

        return pedido;
    }


    public Pedido criar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos(){
        return pedidoRepository.findAll();
    }
    public Optional<Pedido> buscarPorId(Integer id){
        return pedidoRepository.findById(id);
    }

    public void excluir(Integer id){

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pedido não encontrado."
                        ));

        pedidoRepository.delete(pedido);
    }

//    public Pedido atualizar(Integer id, Pedido pedido){
//
//        pedido.setPedidoId(id);
//
//      return pedidoRepository.save(pedido);
//    }

}
