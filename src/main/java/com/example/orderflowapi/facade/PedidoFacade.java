package com.example.orderflowapi.facade;

import com.example.orderflowapi.dto.request.PedidoRequest;
import com.example.orderflowapi.enums.FormaPagamento;
import com.example.orderflowapi.enums.StatusPagamento;
import com.example.orderflowapi.enums.StatusPedido;
import com.example.orderflowapi.exception.EstoqueInsuficienteException;
import com.example.orderflowapi.factory.PagamentoFactory;
import com.example.orderflowapi.model.ItemPedido;
import com.example.orderflowapi.model.Pagamento;
import com.example.orderflowapi.model.Pedido;
import com.example.orderflowapi.model.Produto;
import com.example.orderflowapi.service.ItemPedidoService;
import com.example.orderflowapi.service.PedidoService;
import com.example.orderflowapi.singleton.EstoqueManager;
import com.example.orderflowapi.strategy.EstrategiaPagamento;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoFacade {
    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;
    private final EstoqueManager estoqueManager;
    private final PagamentoFactory pagamentoFactory;



    public PedidoFacade(PedidoService pedidoService,
                        EstoqueManager estoqueManager,
                        PagamentoFactory pagamentoFactory,
                        ItemPedidoService itemPedidoService){

        this.pedidoService = pedidoService;
        this.estoqueManager = estoqueManager;
        this.pagamentoFactory = pagamentoFactory;
        this.itemPedidoService = itemPedidoService;
    }

    public Pedido processarPedido(PedidoRequest request) {

        Pedido pedido = pedidoService.montarPedido(request);

        List<ItemPedido> itens = pedido.getItens();

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedido itemPedido : itens) {

            itemPedidoService.validarProduto(itemPedido);

            Produto produto = itemPedido.getProduto();

            Integer quantidade =
                    itemPedido.getQuantidade();

            boolean estoqueDisponivel =
                    estoqueManager.possuiEstoque(
                            produto,
                            quantidade
                    );

            if (!estoqueDisponivel) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o pedido "
                                + produto.getNome()
                );
            }

            BigDecimal subtotal =
                    itemPedidoService.calcularSubTotal(itemPedido);

            valorTotal = valorTotal.add(subtotal);
        }

        pedido.setValorTotal(valorTotal);

        Pagamento pagamento =
                pedido.getPagamento();

        pagamento.setValor(valorTotal);

        FormaPagamento formaPagamento =
                pagamento.getFormaPagamento();

        EstrategiaPagamento estrategiaPagamento =
                pagamentoFactory.criar(formaPagamento);

        boolean aprovado =
                estrategiaPagamento.pagar(valorTotal);

        if (aprovado) {

            for (ItemPedido itemPedido : itens) {

                Produto produto =
                        itemPedido.getProduto();

                Integer quantidade =
                        itemPedido.getQuantidade();

                estoqueManager.baixarEstoque(
                        produto,
                        quantidade
                );
            }

            pagamento.setStatusPagamento(
                    StatusPagamento.APROVADO
            );

            pagamento.setDataPagamento(
                    LocalDateTime.now()
            );

            pedido.setStatusPedido(
                    StatusPedido.PAGO
            );

        } else {

            pagamento.setStatusPagamento(
                    StatusPagamento.RECUSADO
            );

            pedido.setStatusPedido(
                    StatusPedido.AGUARDANDO_PAGAMENTO
            );
        }

        return pedidoService.criar(pedido);
    }

}
