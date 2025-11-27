package dev.gustavo.toolschallenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormaPagamentoDTO {

        @NotBlank
        private String tipo;

        @NotBlank
        @Pattern(
                regexp = "^[1-9]\\d*$",
                message = "O numero de parcelas deve ser um numeral e maior que 0"
        )
        private String parcelas;
}
