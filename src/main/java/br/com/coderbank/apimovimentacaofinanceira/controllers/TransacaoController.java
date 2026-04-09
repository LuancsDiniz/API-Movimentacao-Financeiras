package br.com.coderbank.apimovimentacaofinanceira.controllers;

import br.com.coderbank.apimovimentacaofinanceira.dtos.requests.TransacaoRequestDTO;
import br.com.coderbank.apimovimentacaofinanceira.dtos.responses.TransacaoResponseDTO;
import br.com.coderbank.apimovimentacaofinanceira.services.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> realizarTransacao(@RequestBody @Valid TransacaoRequestDTO dto){
        return ResponseEntity.status(201).body(transacaoService.realizar(dto));
    }
}
