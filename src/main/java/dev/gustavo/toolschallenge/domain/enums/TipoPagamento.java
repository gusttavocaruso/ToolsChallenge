package dev.gustavo.toolschallenge.domain.enums;

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

    public static TipoPagamento fromDescricao(String tipoDTO) {
        for (TipoPagamento tipo : values()) {
            if (tipo.getDescricao().equals(tipoDTO)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoPagamento inválido: " + tipoDTO);
    }
}
