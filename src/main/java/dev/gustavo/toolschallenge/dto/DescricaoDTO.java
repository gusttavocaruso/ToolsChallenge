package dev.gustavo.toolschallenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DescricaoDTO {

        @Pattern(regexp = "^([1-9]\\d*|0\\.(0?[1-9]|[1-9]\\d?))$",
                message = "pgt nao pode ser menor que zero!")
        @NotBlank
        private String valor;

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        private LocalDateTime dataHora;

        @NotBlank
        private String estabelecimento;

        private String nsu;
        private String codigoAutorizacao;
        private String status;

}
