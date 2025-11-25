package dev.gustavo.toolschallenge.dto;

import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DescricaoDTO {
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String estabelecimento;
    private String nsu;
    private String codigoAutorizacao;
    private StatusTransacao status;
}
