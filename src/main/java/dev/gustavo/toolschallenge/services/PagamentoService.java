package dev.gustavo.toolschallenge.services;

import dev.gustavo.toolschallenge.domain.entities.Transacao;
import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;
import dev.gustavo.toolschallenge.domain.enums.TipoPagamento;
import dev.gustavo.toolschallenge.dto.DescricaoDTO;
import dev.gustavo.toolschallenge.dto.FormaPagamentoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoDTO;
import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import dev.gustavo.toolschallenge.repositories.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public TransacaoWrapperDTO criarPagamento(TransacaoWrapperDTO wrapperDTO) {

        // Convert DTO -> Entity manualmente (sem mapper)
        Transacao t = new Transacao();
        // cartao pode vir mascarado; id externo pode ser informado em wrapper.transacao.id
        t.setCartao(wrapperDTO.transacao().cartao());
        t.setId(Long.valueOf(wrapperDTO.transacao().id()));


        // descricao.valor vem como string; converter para BigDecimal
        BigDecimal valor = new BigDecimal(wrapperDTO.transacao().descricao().valor().replace(',', '.'));
        t.setValor(valor);


        // dataHora e estabelecimento
        // para simplicidade, não convertemos dataHora para LocalDateTime aqui (poderia ser parseado se quiser)
        t.setDataHora(LocalDateTime.now());
        t.setEstabelecimento(wrapperDTO.transacao().descricao().estabelecimento());


        // forma de pagamento
        if (wrapperDTO.transacao().formaPagamento() != null) {
            t.setTipoPagamento(TipoPagamento.AVISTA);
            t.setParcelas(Integer.valueOf(wrapperDTO.transacao().formaPagamento().parcelas()));
        }

        t.setStatus(StatusTransacao.AUTORIZADO);

        // preenche nsu / codigoAutorizacao simples
        t.setNsu(generateNsu());
        t.setCodigoAutorizacao(generateCodigoAutorizacao());

        Transacao saved = transacaoRepository.save(t);

        // Monta DTO de resposta seguindo formato exigido
        TransacaoWrapperDTO resposta = new TransacaoWrapperDTO(montaTransacaoDTO(saved));

        return resposta;
    }

    public TransacaoDTO montaTransacaoDTO(Transacao transacao) {
        DescricaoDTO descricaoDTO = new DescricaoDTO(
                transacao.getValor().toString(),
                transacao.getDataHora().toString(),
                transacao.getEstabelecimento(),
                transacao.getNsu(),
                transacao.getCodigoAutorizacao(),
                transacao.getStatus().name());
        FormaPagamentoDTO pagamentoDTO = new FormaPagamentoDTO(
                transacao.getTipoPagamento().name(),
                transacao.getParcelas().toString());
        return new TransacaoDTO(transacao.getCartao(), transacao.getId().toString(), descricaoDTO, pagamentoDTO);
    }

    private String generateNsu() {
        return String.valueOf(Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextLong())).substring(0,9);
    }

    private String generateCodigoAutorizacao() {
        return String.valueOf(Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextInt())).substring(0,8);
    }

//    public boolean validaCamposDTO(TransacaoWrapperDTO wrapperDTO) {
//        wrapperDTO.transacao().descricao()
//    }
}
