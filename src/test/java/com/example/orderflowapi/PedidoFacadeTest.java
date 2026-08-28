package com.example.orderflowapi;

import com.example.orderflowapi.dto.request.PagamentoRequest;
import com.example.orderflowapi.dto.request.PedidoRequest;
import com.example.orderflowapi.enums.FormaPagamento;
import com.example.orderflowapi.enums.StatusPagamento;
import com.example.orderflowapi.enums.StatusPedido;
import com.example.orderflowapi.exception.EstoqueInsuficienteException;
import com.example.orderflowapi.facade.PedidoFacade;
import com.example.orderflowapi.factory.PagamentoFactory;
import com.example.orderflowapi.model.ItemPedido;
import com.example.orderflowapi.model.Pagamento;
import com.example.orderflowapi.model.Pedido;
import com.example.orderflowapi.model.Produto;
import com.example.orderflowapi.service.ClienteService;
import com.example.orderflowapi.service.ItemPedidoService;
import com.example.orderflowapi.service.PedidoService;
import com.example.orderflowapi.singleton.EstoqueManager;
import com.example.orderflowapi.strategy.EstrategiaPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoFacadeTest {

    @Mock
    private PedidoService pedidoService;

    @Mock
    private ItemPedidoService itemPedidoService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private EstoqueManager estoqueManager;

    @Mock
    private PagamentoFactory pagamentoFactory;

    @Mock
    private EstrategiaPagamento estrategiaPagamento;

    @InjectMocks
    private PedidoFacade pedidoFacade;

    private Produto produto;
    private ItemPedido item;
    private Pagamento pagamento;
    private Pedido pedido;
    private PedidoRequest request;

    @BeforeEach
    void configurarCenario() {
        produto = Produto.builder()
                .produtoId(5)
                .nome("Notebook")
                .preco(new BigDecimal("180.50"))
                .build();

        item = ItemPedido.builder()
                .produto(produto)
                .quantidade(1)
                .precoUnitario(produto.getPreco())
                .build();

        pagamento = Pagamento.builder()
                .formaPagamento(FormaPagamento.PIX)
                .statusPagamento(StatusPagamento.PENDENTE)
                .build();

        pedido = Pedido.builder()
                .pedidoId(1)
                .valorTotal(new BigDecimal("180.50"))
                .statusPedido(StatusPedido.CRIADO)
                .pagamento(pagamento)
                .itens(List.of(item))
                .build();

        request = PedidoRequest.builder()
                .clienteId(1)
                .pagamento(
                        PagamentoRequest.builder()
                                .formaPagamento(FormaPagamento.PIX)
                                .build()
                )
                .build();
    }

    @Test
    void deveProcessarPedidoComSucesso() {

        when(pedidoService.montarPedido(request))
                .thenReturn(pedido);

        when(itemPedidoService.calcularSubTotal(item))
                .thenReturn(new BigDecimal("180.50"));

        when(estoqueManager.possuiEstoque(produto, 1))
                .thenReturn(true);

        when(pagamentoFactory.criar(FormaPagamento.PIX))
                .thenReturn(estrategiaPagamento);

        when(estrategiaPagamento.pagar(new BigDecimal("180.50")))
                .thenReturn(true);

        pedidoFacade.processarPedido(request);

        verify(itemPedidoService)
                .calcularSubTotal(item);

        verify(estoqueManager)
                .baixarEstoque(produto, 1);

        verify(estrategiaPagamento)
                .pagar(new BigDecimal("180.50"));
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueForInsuficiente() {

        when(pedidoService.montarPedido(request))
                .thenReturn(pedido);

        when(estoqueManager.possuiEstoque(produto, 1))
                .thenReturn(false);

        EstoqueInsuficienteException exception = assertThrows(
                EstoqueInsuficienteException.class,
                () -> pedidoFacade.processarPedido(request)
        );

        assertEquals(
                "Estoque insuficiente para o pedido Notebook",
                exception.getMessage()
        );



    }

    @Test
    void deveProcessarPagamento() {

        when(pedidoService.montarPedido(request))
                .thenReturn(pedido);

        when(itemPedidoService.calcularSubTotal(item))
                .thenReturn(new BigDecimal("180.50"));

        when(estoqueManager.possuiEstoque(produto, 1))
                .thenReturn(true);

        when(pagamentoFactory.criar(FormaPagamento.PIX))
                .thenReturn(estrategiaPagamento);

        when(estrategiaPagamento.pagar(new BigDecimal("180.50")))
                .thenReturn(true);

        pedidoFacade.processarPedido(request);

        verify(itemPedidoService)
                .calcularSubTotal(item);

        verify(estoqueManager)
                .baixarEstoque(produto, 1);

        verify(estrategiaPagamento)
                .pagar(new BigDecimal("180.50"));

        verify(pagamentoFactory)
                .criar(FormaPagamento.PIX);

        assertEquals(
                StatusPagamento.APROVADO,
                pagamento.getStatusPagamento()
        );

        assertEquals(
                StatusPedido.PAGO,
                pedido.getStatusPedido()
        );
    }

    @Test
    void deveManterPedidoAguardandoPagamentoQuandoPagamentoForRecusado(){

        when(pedidoService.montarPedido(request))
                .thenReturn(pedido);

        when(itemPedidoService.calcularSubTotal(item))
                .thenReturn(new BigDecimal("180.50"));

        when(estoqueManager.possuiEstoque(produto, 1))
                .thenReturn(true);

        when(pagamentoFactory.criar(FormaPagamento.PIX))
                .thenReturn(estrategiaPagamento);

        when(estrategiaPagamento.pagar(new BigDecimal("180.50")))
                .thenReturn(false);

        pedidoFacade.processarPedido(request);

        verify(itemPedidoService)
                .calcularSubTotal(item);

        verify(estoqueManager)
                .baixarEstoque(produto, 1);

        verify(estrategiaPagamento)
                .pagar(new BigDecimal("180.50"));

        assertEquals(
                StatusPagamento.RECUSADO,
                pagamento.getStatusPagamento()
        );

        assertEquals(
                StatusPedido.AGUARDANDO_PAGAMENTO,
                pedido.getStatusPedido()
        );
    }

}