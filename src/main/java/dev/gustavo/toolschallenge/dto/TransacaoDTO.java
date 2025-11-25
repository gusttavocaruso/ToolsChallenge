package dev.gustavo.toolschallenge.dto;

public record TransacaoDTO(String cartao, String id,
                           DescricaoDTO descricao,
                           FormaPagamentoDTO formaPagamento) {
}
