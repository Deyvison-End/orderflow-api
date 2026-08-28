package com.example.orderflowapi.dto.response;

import com.example.orderflowapi.enums.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

    private Integer pedidoId;
    private Integer clienteId;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private StatusPedido statusPedido;
    private List<ItemPedidoResponse> itens;
}