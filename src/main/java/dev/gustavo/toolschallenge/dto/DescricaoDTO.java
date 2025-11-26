package dev.gustavo.toolschallenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record DescricaoDTO(

        @Pattern(regexp = "^([1-9]\\d*|0\\.(0?[1-9]|[1-9]\\d?))$",
                message = "pgt nao pode ser menor que zero!")
        @NotBlank String valor,

        @NotNull @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataHora,

        @NotBlank
        String estabelecimento,
        String nsu,
        String codigoAutorizacao,
        String status) {}
