package dev.gustavo.toolschallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransacaoDTO(
        @NotBlank String cartao,
        @NotBlank @Size(min = 16, max = 16) String id,
        @NotNull @Valid DescricaoDTO descricao,
        @NotNull @Valid FormaPagamentoDTO formaPagamento) {}