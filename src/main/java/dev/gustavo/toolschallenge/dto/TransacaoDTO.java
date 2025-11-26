package dev.gustavo.toolschallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransacaoDTO {

    @NotBlank
    private String cartao;

    @NotBlank
    @Size(min = 16, max = 16)
    private String id;

    @NotNull
    @Valid
    private DescricaoDTO descricao;

    @NotNull
    @Valid
    private FormaPagamentoDTO formaPagamento;
}