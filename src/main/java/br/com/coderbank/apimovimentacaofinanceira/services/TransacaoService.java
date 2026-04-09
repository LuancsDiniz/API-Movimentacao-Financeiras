package br.com.coderbank.apimovimentacaofinanceira.services;

import br.com.coderbank.apimovimentacaofinanceira.dtos.requests.TransacaoRequestDTO;
import br.com.coderbank.apimovimentacaofinanceira.dtos.responses.TransacaoResponseDTO;
import br.com.coderbank.apimovimentacaofinanceira.entities.Conta;
import br.com.coderbank.apimovimentacaofinanceira.entities.Transacao;
import br.com.coderbank.apimovimentacaofinanceira.enums.StatusTransacao;
import br.com.coderbank.apimovimentacaofinanceira.exceptions.RecursoNaoEncontradoException;
import br.com.coderbank.apimovimentacaofinanceira.exceptions.RegraNegocioException;
import br.com.coderbank.apimovimentacaofinanceira.repositories.ContaRepository;
import br.com.coderbank.apimovimentacaofinanceira.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransacaoService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public TransacaoResponseDTO realizar(TransacaoRequestDTO dto) {

        Conta contaOrigem = dto.contaOrigemId() != null ? buscar(dto.contaOrigemId()) : null;
        Conta contaDestino = dto.contaDestinoId() != null ? buscar(dto.contaDestinoId()) : null;

        switch (dto.tipo()) {

            case DEPOSITO -> {
                validarDestino(contaDestino);
                contaDestino.setSaldo(contaDestino.getSaldo().add(dto.valor()));
            }
            case SAQUE -> {
                validarOrigem(contaOrigem);
                validarSaldo(contaOrigem, dto.valor());
                contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(dto.valor()));
            }
            case TRANSFERENCIA -> {
                validarOrigem(contaOrigem);
                validarDestino(contaDestino);
                validarTransferencia(contaOrigem, contaDestino);
                validarSaldo(contaOrigem, dto.valor());

                contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(dto.valor()));
                contaDestino.setSaldo(contaDestino.getSaldo().add(dto.valor()));
            }

        }
        var salvarEntity = salvar(dto, contaOrigem, contaDestino);

        return paraResponseDTO(salvarEntity);
    }

    private void validarOrigem(Conta conta) {
        if (conta == null) {
            throw new RegraNegocioException("Conta de origem obrigatória");
        }
    }

    private void validarDestino(Conta conta) {
        if (conta == null) {
            throw new RegraNegocioException("Conta de destino obrigatória");
        }
    }

    private void validarSaldo(Conta conta, BigDecimal valor) {
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RegraNegocioException("Saldo insuficiente");
        }
    }

    private void validarTransferencia(Conta origem, Conta destino) {
        if (origem.getId().equals(destino.getId())) {
            throw new RegraNegocioException("Mesma conta");
        }
        if (origem.getIdCliente().equals(destino.getIdCliente())) {
            throw new RegraNegocioException("Mesmo cliente");
        }
    }


    private Conta buscar(UUID id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));
    }

    private Transacao salvar(TransacaoRequestDTO dto, Conta origem, Conta destino) {
        var transacaoEntidade = paraEntity(dto, origem, destino);
        return transacaoRepository.save(transacaoEntidade);
    }

    private Transacao paraEntity(TransacaoRequestDTO dto, Conta origem, Conta destino) {
        return Transacao.builder()
                .tipoTransacao(dto.tipo())
                .valor(dto.valor())
                .descricao(dto.descricao())
                .dataHora(LocalDateTime.now())
                .statusTransacao(StatusTransacao.CONCLUIDA)
                .contaOrigemId(origem != null ? origem.getId() : null)
                .contaDestinoId(destino != null ? destino.getId() : null)
                .build();
    }

    private TransacaoResponseDTO paraResponseDTO(Transacao transacao) {
        return new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getTipoTransacao(),
                transacao.getValor(),
                transacao.getDataHora(),
                transacao.getStatusTransacao().name()
        );
    }
}
