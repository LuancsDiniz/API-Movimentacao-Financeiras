package br.com.coderbank.apimovimentacaofinanceira.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ContaResponseDTO(
        UUID idConta,
        String numeroAgencia,
        String numeroConta,
        BigDecimal saldo,
        LocalDateTime dataCriacao
) {

}
