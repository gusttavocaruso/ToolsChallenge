package dev.gustavo.toolschallenge.domain.enums;

import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import dev.gustavo.toolschallenge.execptions.RegraNegocioException;

public enum TipoPagamento {
    AVISTA("AVISTA"),
    PARCELADO_LOJA("PARCELADO LOJA"),
    PARCELADO_EMISSOR("PARCELADO EMISSOR");

    private String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoPagamento fromDescricao(TransacaoWrapperDTO dto) {
        for (TipoPagamento tipo : values()) {
            if (tipo.getDescricao().equals(dto.getTransacao().getFormaPagamento().getTipo())) {
                return tipo;
            }
        }
        throw new RegraNegocioException("Atributo 'formaPagamento.tipo' inválido." +
                " Aceita: 'AVISTA', 'PARCELADO LOJA' ou 'PARCELADO EMISSOR' ", dto);
    }
}
