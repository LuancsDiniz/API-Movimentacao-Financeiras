package br.com.coderbank.apimovimentacaofinanceira.controllers;

import br.com.coderbank.apimovimentacaofinanceira.dtos.requests.ContaRequestDTO;
import br.com.coderbank.apimovimentacaofinanceira.dtos.responses.ContaResponseDTO;
import br.com.coderbank.apimovimentacaofinanceira.dtos.responses.TransacaoResponseDTO;
import br.com.coderbank.apimovimentacaofinanceira.services.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody @Valid ContaRequestDTO dto) {
        return ResponseEntity.status(201).body(contaService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(contaService.buscar(id));
    }

    @GetMapping("{id}/extrato")
    public ResponseEntity<List<TransacaoResponseDTO>> extrato(@PathVariable UUID id){
        return ResponseEntity.ok(contaService.extrato(id));
    }
}
