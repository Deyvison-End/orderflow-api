package com.example.orderflowapi.strategy;

import java.math.BigDecimal;

public class PagamentoCartao implements EstrategiaPagamento {

    @Override
    public Boolean pagar(BigDecimal valor) {

        System.out.println("Pagamento via Cartão no valor de " + valor);
        return valor.compareTo(BigDecimal.ZERO) > 0;
    }
}