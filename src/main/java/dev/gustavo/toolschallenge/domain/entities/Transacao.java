package dev.gustavo.toolschallenge.domain.entities;

import dev.gustavo.toolschallenge.domain.enums.TipoPagamento;
import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_transacoes")
public class Transacao {

    @Id
    private Long id;

    @Column(name = "cartao")
    private String cartao;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "estabelecimento")
    private String estabelecimento;

    @Column(name = "nsu")
    private String nsu;

    @Column(name = "codigo_autorizacao")
    private String codigoAutorizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTransacao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento")
    private TipoPagamento tipoPagamento;

    @Column(name = "parcelas")
    private Integer parcelas;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}


