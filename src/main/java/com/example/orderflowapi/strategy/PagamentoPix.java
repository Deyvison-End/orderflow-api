package com.example.orderflowapi.strategy;

import java.math.BigDecimal;

public class PagamentoPix implements EstrategiaPagamento {

    @Override
    public Boolean pagar(BigDecimal valor) {
        System.out.println("Pagamento via Pix no valor de " + valor);
        return valor.compareTo(BigDecimal.ZERO) > 0;
    }
}
