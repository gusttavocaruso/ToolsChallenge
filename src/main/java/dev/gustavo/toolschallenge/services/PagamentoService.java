package dev.gustavo.toolschallenge.services;

import dev.gustavo.toolschallenge.domain.entities.Transacao;
import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;
import dev.gustavo.toolschallenge.domain.enums.TipoPagamento;
import dev.gustavo.toolschallenge.dto.DescricaoDTO;
import dev.gustavo.toolschallenge.dto.FormaPagamentoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import dev.gustavo.toolschallenge.execptions.UniqueIdException;
import dev.gustavo.toolschallenge.repositories.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PagamentoService {

    private final TransacaoRepository transacaoRepository;

    public PagamentoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public List<TransacaoWrapperDTO> listarTodos() {
        List<Transacao> transacaoList = transacaoRepository.findAll();
        List<TransacaoWrapperDTO> transacaoDTOList = new ArrayList<>();

        if (!transacaoList.isEmpty()) {
            for (Transacao transacao : transacaoList) {
                transacaoDTOList.add(new TransacaoWrapperDTO(montaTransacaoDTO(transacao)));
            }
        }
        return transacaoDTOList;
    }

    @Transactional
    public TransacaoWrapperDTO criarPagamento(TransacaoWrapperDTO dtoRequest) {
        Transacao transacao = montaTransacaoObj(dtoRequest);
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        return new TransacaoWrapperDTO(montaTransacaoDTO(transacaoSalva));
    }

    public TransacaoDTO montaTransacaoDTO(Transacao transacao) {
        DescricaoDTO descricaoDTO = new DescricaoDTO(
                transacao.getValor().toString(),
                transacao.getDataHora(),
                transacao.getEstabelecimento(),
                transacao.getNsu(),
                transacao.getCodigoAutorizacao(),
                StatusTransacao.AUTORIZADO.name());
        FormaPagamentoDTO pagamentoDTO = new FormaPagamentoDTO(
                transacao.getTipoPagamento().getDescricao(),
                transacao.getParcelas().toString());
        return new TransacaoDTO(transacao.getCartao(), transacao.getId().toString(), descricaoDTO, pagamentoDTO);
    }

    public Transacao montaTransacaoObj(TransacaoWrapperDTO dto) {
        Transacao transacao = new Transacao();

        transacao.setId(validaIDUnico(dto));
        transacao.setCartao(dto.getTransacao().getCartao());
        transacao.setValor(new BigDecimal(dto.getTransacao().getDescricao().getValor().replace(',', '.')));
        transacao.setDataHora(dto.getTransacao().getDescricao().getDataHora());
        transacao.setEstabelecimento(dto.getTransacao().getDescricao().getEstabelecimento());
        transacao.setTipoPagamento(TipoPagamento.fromDescricao(dto.getTransacao().getFormaPagamento().getTipo()));
        transacao.setParcelas(Integer.valueOf(dto.getTransacao().getFormaPagamento().getParcelas()));

        transacao.setNsu(generateNsu());
        transacao.setCodigoAutorizacao(generateCodigoAutorizacao());
        return transacao;
    }

    public Long validaIDUnico(TransacaoWrapperDTO dto) {
        boolean idJaExiste = this.listarTodos().stream().anyMatch(tr -> tr.getTransacao().getId().equals(dto.getTransacao().getId()));
        if (idJaExiste) {
            throw new UniqueIdException("ID JA EXISTE", dto);
        }
        return Long.valueOf(dto.getTransacao().getId());
    }

    private String generateNsu() {
        return String.valueOf(Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextLong())).substring(0,9);
    }

    private String generateCodigoAutorizacao() {
        return String.valueOf(Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextInt())).substring(0,8);
    }

}
