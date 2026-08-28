package com.example.orderflowapi.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoResponse {

    private Integer produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;
}