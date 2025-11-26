package dev.gustavo.toolschallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record TransacaoWrapperDTO(@NotNull @Valid TransacaoDTO transacao) {}
