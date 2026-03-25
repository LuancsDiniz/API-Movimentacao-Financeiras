package br.com.coderbank.apimovimentacaofinanceira.repositories;

import br.com.coderbank.apimovimentacaofinanceira.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    List<Transacao> findByContaOrigemIdOrContaDestinoId(UUID origem, UUID destino);
}
