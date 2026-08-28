package com.example.orderflowapi.dto.request;

import com.example.orderflowapi.enums.FormaPagamento;
import com.example.orderflowapi.model.Pagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoRequest {

    @NotNull(message = "O cliente é obrigatório.")
    private Integer clienteId;

    @NotNull(message = "O pagamento é Obrigatório.")
    @Valid
    private PagamentoRequest pagamento;
    @NotEmpty(message = "O pedido deve possuir pelo menos um item.")
    @Valid
    private List<ItemPedidoRequest> itens;
}