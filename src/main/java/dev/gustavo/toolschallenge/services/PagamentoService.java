package dev.gustavo.toolschallenge.services;

import dev.gustavo.toolschallenge.domain.entities.Transacao;
import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;
import dev.gustavo.toolschallenge.domain.enums.TipoPagamento;
import dev.gustavo.toolschallenge.dto.DescricaoDTO;
import dev.gustavo.toolschallenge.dto.FormaPagamentoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
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

        try {
            transacao.setStatus(StatusTransacao.AUTORIZADO);
            Transacao transacaoSalva = transacaoRepository.save(transacao);
            return new TransacaoWrapperDTO(montaTransacaoDTO(transacaoSalva));
        } catch (Exception e) {
            transacao.setStatus(StatusTransacao.NEGADO);
            return new TransacaoWrapperDTO(montaTransacaoDTO(transacao));
        }

    }

    public TransacaoDTO montaTransacaoDTO(Transacao transacao) {
        DescricaoDTO descricaoDTO = new DescricaoDTO(
                transacao.getValor().toString(),
                transacao.getDataHora(),
                transacao.getEstabelecimento(),
                transacao.getNsu(),
                transacao.getCodigoAutorizacao(),
                transacao.getStatus().name());
        FormaPagamentoDTO pagamentoDTO = new FormaPagamentoDTO(
                transacao.getTipoPagamento().getDescricao(),
                transacao.getParcelas().toString());
        return new TransacaoDTO(transacao.getCartao(), transacao.getId().toString(), descricaoDTO, pagamentoDTO);
    }

    public Transacao montaTransacaoObj(TransacaoWrapperDTO dto) {
        Transacao transacao = new Transacao();

        transacao.setId(validaIDUnico(dto.transacao().id()));
        transacao.setCartao(dto.transacao().cartao());
        transacao.setValor(new BigDecimal(dto.transacao().descricao().valor().replace(',', '.')));
        transacao.setDataHora(dto.transacao().descricao().dataHora());
        transacao.setEstabelecimento(dto.transacao().descricao().estabelecimento());
        transacao.setTipoPagamento(TipoPagamento.fromDescricao(dto.transacao().formaPagamento().tipo()));
        transacao.setParcelas(Integer.valueOf(dto.transacao().formaPagamento().parcelas()));

        transacao.setNsu(generateNsu());
        transacao.setCodigoAutorizacao(generateCodigoAutorizacao());
        return transacao;
    }

    public Long validaIDUnico(String id) {
        boolean idJaExiste = this.listarTodos().stream().anyMatch(tr -> tr.transacao().id().equals(id));
        if (idJaExiste) {
            throw new RuntimeException("id ja existe");
        }
        return Long.valueOf(id);
    }

    private String generateNsu() {
        return String.valueOf(Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextLong())).substring(0,9);
    }

    private String generateCodigoAutorizacao() {
        return String.valueOf(Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextInt())).substring(0,8);
    }

    public static Integer parseParcelaInt(String parcelas) {
        try {
            int number = Integer.parseInt(parcelas);
            return number > 0 ? number : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
