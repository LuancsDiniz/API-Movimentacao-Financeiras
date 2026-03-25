package br.com.coderbank.apimovimentacaofinanceira.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ContaRequestDTO(
        @NotNull UUID idCliente
        ) {
}
