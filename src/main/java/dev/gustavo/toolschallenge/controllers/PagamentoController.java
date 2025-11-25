package dev.gustavo.toolschallenge.controllers;

import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import dev.gustavo.toolschallenge.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoWrapperDTO> criarPagamento(@Valid @RequestBody TransacaoWrapperDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoService.criarPagamento(dto));
    }

    @GetMapping
    public ResponseEntity<List<TransacaoWrapperDTO>> listarTransacoes() {
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoService.listarTodos());
    }

}
