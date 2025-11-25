package dev.gustavo.toolschallenge.services;

import dev.gustavo.toolschallenge.repositories.TransacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final TransacaoRepository transacaoRepository;

    public PagamentoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }
}
