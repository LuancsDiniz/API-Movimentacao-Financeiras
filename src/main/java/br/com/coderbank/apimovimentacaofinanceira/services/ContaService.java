package br.com.coderbank.apimovimentacaofinanceira.services;

import br.com.coderbank.apimovimentacaofinanceira.dtos.requests.ContaRequestDTO;
import br.com.coderbank.apimovimentacaofinanceira.dtos.responses.ContaResponseDTO;
import br.com.coderbank.apimovimentacaofinanceira.dtos.responses.TransacaoResponseDTO;
import br.com.coderbank.apimovimentacaofinanceira.entities.Conta;
import br.com.coderbank.apimovimentacaofinanceira.entities.Transacao;
import br.com.coderbank.apimovimentacaofinanceira.exceptions.RecursoNaoEncontradoException;
import br.com.coderbank.apimovimentacaofinanceira.repositories.ContaRepository;
import br.com.coderbank.apimovimentacaofinanceira.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaResponseDTO criar(ContaRequestDTO dto) {

        Conta conta = Conta.builder()
                .idCliente(dto.idCliente())
                .numeroAgencia("0001")
                .numeroConta(gerar())
                .saldo(BigDecimal.ZERO)
                .dataCriacao(LocalDateTime.now())
                .build();

        contaRepository.save(conta);

        return paraContaResponse(conta);
    }

    public ContaResponseDTO buscar(UUID id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));

        return paraContaResponse(conta);
    }

    public List<TransacaoResponseDTO> extrato(UUID id){

        return transacaoRepository.findByContaOrigemIdOrContaDestinoId(id, id)
                .stream()
                .map(this::paraTransacaoResponse)
                .toList();
    }

    private String gerar() {

        String numero;

        do {
            int n = new Random().nextInt(1000000);
            String s = String.format("%06d", n);
            numero = s.substring(0, 5) + "-" + s.charAt(5);

        } while (contaRepository.existsByNumeroConta(numero));

        return numero;
    }

    private ContaResponseDTO paraContaResponse(Conta conta) {
        return new ContaResponseDTO(
                conta.getId(),
                conta.getNumeroAgencia(),
                conta.getNumeroConta(),
                conta.getSaldo(),
                conta.getDataCriacao()
        );
    }

    private TransacaoResponseDTO paraTransacaoResponse(Transacao transacao){
        return new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getTipoTransacao(),
                transacao.getValor(),
                transacao.getDataHora(),
                transacao.getStatusTransacao().name());
    }
}
