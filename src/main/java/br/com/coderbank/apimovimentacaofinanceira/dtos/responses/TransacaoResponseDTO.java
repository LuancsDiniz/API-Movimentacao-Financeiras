package br.com.coderbank.apimovimentacaofinanceira.dtos.responses;

import br.com.coderbank.apimovimentacaofinanceira.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoResponseDTO(
        UUID idTransacao,
        TipoTransacao tipo,
        BigDecimal valor,
        LocalDateTime dataHora,
        String status
) {

}
