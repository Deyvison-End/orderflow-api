package com.example.orderflowapi.mapper;

import com.example.orderflowapi.dto.request.PedidoRequest;
import com.example.orderflowapi.dto.response.ItemPedidoResponse;
import com.example.orderflowapi.dto.response.PedidoResponse;
import com.example.orderflowapi.model.ItemPedido;
import com.example.orderflowapi.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido toEntity(PedidoRequest request) {

        return Pedido.builder()
                .build();
    }

    public PedidoResponse toResponse(Pedido pedido) {

        List<ItemPedidoResponse> itens = pedido.getItens()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return PedidoResponse.builder()
                .pedidoId(pedido.getPedidoId())
                .clienteId(pedido.getCliente().getClienteId())
                .dataPedido(pedido.getDataPedido())
                .valorTotal(pedido.getValorTotal())
                .statusPedido(pedido.getStatusPedido())
                .itens(itens)
                .build();
    }

    private ItemPedidoResponse toItemResponse(ItemPedido item) {

        return ItemPedidoResponse.builder()
                .produtoId(item.getProduto().getProdutoId())
                .quantidade(item.getQuantidade())
                .precoUnitario(item.getPrecoUnitario())
                .subtotal(item.getSubtotal())
                .build();
    }
}