package dev.gustavo.toolschallenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FormaPagamentoDTO(
        @NotBlank
        String tipo,

        @NotBlank @Pattern(regexp = "^[1-9]\\d*$", message = "Numero de parcelas deve ser um nmeral")
        String parcelas) {}
