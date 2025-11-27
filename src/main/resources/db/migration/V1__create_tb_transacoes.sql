CREATE TABLE IF NOT EXISTS tb_transacoes (
    id BIGINT PRIMARY KEY,
    cartao VARCHAR(255),
    valor NUMERIC(15, 2),
    data_hora TIMESTAMP,
    estabelecimento VARCHAR(255),
    nsu VARCHAR(50),
    codigo_autorizacao VARCHAR(50),
    status VARCHAR(50),
    tipo_pagamento VARCHAR(50),
    parcelas INTEGER,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);
