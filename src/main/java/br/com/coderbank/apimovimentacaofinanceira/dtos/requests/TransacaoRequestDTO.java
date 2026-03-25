package br.com.coderbank.apimovimentacaofinanceira.dtos.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record TransacaoRequestDTO(
        @NotNull
        @DecimalMin("0.01")
        BigDecimal valor,

        UUID contaDeOrigem,
        UUID contaDeDestino,

        @Size(max = 100)
        String descricao
) {
}
