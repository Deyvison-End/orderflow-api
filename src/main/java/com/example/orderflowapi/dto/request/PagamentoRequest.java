package com.example.orderflowapi.dto.request;

import com.example.orderflowapi.enums.FormaPagamento;
import com.example.orderflowapi.enums.StatusPagamento;
import com.example.orderflowapi.model.Pedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoRequest {

    @NotNull(message = "É necessário informar a Forma de Pagamento")
    private FormaPagamento formaPagamento;
}
