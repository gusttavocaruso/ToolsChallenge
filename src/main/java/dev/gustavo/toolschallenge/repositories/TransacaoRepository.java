package dev.gustavo.toolschallenge.repositories;

import dev.gustavo.toolschallenge.domain.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
