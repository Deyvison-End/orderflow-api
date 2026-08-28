package com.example.orderflowapi.dto.response;

import com.example.orderflowapi.enums.FormaPagamento;
import com.example.orderflowapi.enums.StatusPagamento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoResponse {

    private Integer pagamentoId;
    private Integer pedidoId;
    private FormaPagamento formaPagamento;
    private StatusPagamento statusPagamento;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
}
