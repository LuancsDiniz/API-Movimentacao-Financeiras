package br.com.coderbank.apimovimentacaofinanceira.dtos.requests;

import br.com.coderbank.apimovimentacaofinanceira.enums.TipoTransacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record TransacaoRequestDTO(
        @NotNull(message = "Tipo é obrigatório")
        TipoTransacao tipo,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal valor,

        UUID contaOrigemId,
        UUID contaDestinoId,

        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
        String descricao
) {
}
