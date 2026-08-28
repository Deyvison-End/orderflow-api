package com.example.orderflowapi.model;

import com.example.orderflowapi.enums.FormaPagamento;
import com.example.orderflowapi.enums.StatusPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pagamentoId;
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
    private FormaPagamento formaPagamento;
    private StatusPagamento statusPagamento;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;

}
