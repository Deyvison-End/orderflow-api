package com.example.orderflowapi.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private Integer status;

    private String mensagem;

    private LocalDateTime dataHora;

    private List<String> erros;

}