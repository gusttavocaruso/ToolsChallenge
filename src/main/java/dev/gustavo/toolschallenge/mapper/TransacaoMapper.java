package dev.gustavo.toolschallenge.mapper;

import dev.gustavo.toolschallenge.domain.entities.Transacao;
import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;
import dev.gustavo.toolschallenge.domain.enums.TipoPagamento;
import dev.gustavo.toolschallenge.dto.DescricaoDTO;
import dev.gustavo.toolschallenge.dto.FormaPagamentoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransacaoMapper {

    public TransacaoWrapperDTO toDTO(Transacao transacao) {
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
        TransacaoDTO transacaoDTO = new TransacaoDTO(
                transacao.getCartao(),
                transacao.getId().toString(),
                descricaoDTO, pagamentoDTO);
        return new TransacaoWrapperDTO(transacaoDTO);
    }

    public Transacao toEntity(TransacaoWrapperDTO dto) {
        Transacao transacao = new Transacao();

        transacao.setId(Long.valueOf(dto.getTransacao().getId()));
        transacao.setCartao(dto.getTransacao().getCartao());
        transacao.setValor(new BigDecimal(dto.getTransacao().getDescricao().getValor().replace(',', '.')));
        transacao.setDataHora(dto.getTransacao().getDescricao().getDataHora());
        transacao.setEstabelecimento(dto.getTransacao().getDescricao().getEstabelecimento());
        transacao.setTipoPagamento(TipoPagamento.fromDescricao(dto));
        transacao.setParcelas(Integer.valueOf(dto.getTransacao().getFormaPagamento().getParcelas()));
        transacao.setStatus(StatusTransacao.AUTORIZADO);

        transacao.setNsu(generateNsu());
        transacao.setCodigoAutorizacao(generateCodigoAutorizacao());
        return transacao;
    }

    private String generateNsu() {
        return String.valueOf(Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextLong())).substring(0,9);
    }

    private String generateCodigoAutorizacao() {
        return String.valueOf(Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextInt())).substring(0,8);
    }
}
