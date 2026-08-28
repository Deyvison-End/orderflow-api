package com.example.orderflowapi.strategy;

import java.math.BigDecimal;

public class PagamentoBoleto implements EstrategiaPagamento {

    @Override
    public Boolean pagar(BigDecimal valor) {

        System.out.println("Pagamento via Boleto no valor de " + valor);
        return valor.compareTo(BigDecimal.ZERO) > 0;
    }
}
