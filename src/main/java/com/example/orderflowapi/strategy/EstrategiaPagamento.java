package com.example.orderflowapi.strategy;

import java.math.BigDecimal;

public interface EstrategiaPagamento {

    Boolean pagar(BigDecimal valor);

}
