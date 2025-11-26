package dev.gustavo.toolschallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TransacaoWrapperDTO {

    @NotNull
    @Valid
    private TransacaoDTO transacao;
}