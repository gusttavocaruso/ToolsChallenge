package dev.gustavo.toolschallenge.services;

import dev.gustavo.toolschallenge.domain.entities.Transacao;
import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;
import dev.gustavo.toolschallenge.dto.EstornoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import dev.gustavo.toolschallenge.execptions.IdNaoExisteException;
import dev.gustavo.toolschallenge.execptions.JaEstornadoExecption;
import dev.gustavo.toolschallenge.execptions.UniqueIdException;
import dev.gustavo.toolschallenge.mapper.TransacaoMapper;
import dev.gustavo.toolschallenge.repositories.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    private final TransacaoRepository repository;
    private final TransacaoMapper mapper;

    public PagamentoService(TransacaoRepository repository, TransacaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TransacaoWrapperDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public TransacaoWrapperDTO listarById(String id) {
        Transacao transacao = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IdNaoExisteException(id));

        return mapper.toDTO(transacao);
    }

    @Transactional
    public TransacaoWrapperDTO criarPagamento(TransacaoWrapperDTO dtoRequest) {

        if (repository.existsById(Long.valueOf(dtoRequest.getTransacao().getId()))) {
            throw new UniqueIdException(dtoRequest);
        }

        Transacao transacao = mapper.toEntity(dtoRequest);
        Transacao transacaoSalva = repository.save(transacao);

        return mapper.toDTO(transacaoSalva);
    }

    @Transactional
    public TransacaoWrapperDTO gerarEstorno(EstornoDTO dtoRequest) {
        String idRequest = dtoRequest.getId();
        Transacao transacao = repository.findById(Long.valueOf(idRequest))
                .orElseThrow(() -> new IdNaoExisteException(idRequest));

        if (transacao.getStatus().equals(StatusTransacao.CANCELADO)) {
            throw new JaEstornadoExecption(idRequest);
        }

        transacao.setStatus(StatusTransacao.CANCELADO);
        Transacao transacaoSalva = repository.save(transacao);

        return mapper.toDTO(transacaoSalva);
    }
}
