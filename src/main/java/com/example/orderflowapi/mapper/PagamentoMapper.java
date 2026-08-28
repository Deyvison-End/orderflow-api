package com.example.orderflowapi.mapper;

import com.example.orderflowapi.dto.request.PagamentoRequest;
import com.example.orderflowapi.dto.response.PagamentoResponse;
import com.example.orderflowapi.model.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public Pagamento toEntity(PagamentoRequest request){
        return Pagamento.builder()
                .formaPagamento(request.getFormaPagamento())
                .build();
    }

    public PagamentoResponse toResponse(Pagamento pagamento){

        return PagamentoResponse.builder()
                .pagamentoId(pagamento.getPagamentoId())
                .pedidoId(pagamento.getPedido().getPedidoId())
                .dataPagamento(pagamento.getDataPagamento())
                .formaPagamento(pagamento.getFormaPagamento())
                .statusPagamento(pagamento.getStatusPagamento())
                .valor(pagamento.getValor())
                .build();
    }
}
