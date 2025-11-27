package dev.gustavo.toolschallenge.execptions;

import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;
import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UniqueIdException.class)
    public ResponseEntity<TransacaoWrapperDTO> handleNegocio(UniqueIdException ex) {

        ex.getDto().getTransacao().getDescricao().setStatus(StatusTransacao.NEGADO.name());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header(String.format("Erro encontrado: %s", ex.getMessage()))
                .body(ex.getDto());
    }

    @ExceptionHandler(IdNaoExisteException.class)
    public ResponseEntity<String> handleIdNaoExiste(IdNaoExisteException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(JaEstornadoExecption.class)
    public ResponseEntity<String> handleJaEstornado(JaEstornadoExecption ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TransacaoWrapperDTO> handleValidation(MethodArgumentNotValidException ex) {

        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> String.format(
                        "Erro no atributo '%s':\n %s (valor recebido: %s)",
                        err.getField(),
                        err.getDefaultMessage(),
                        err.getRejectedValue()))
                .toList();

        Object target = ex.getBindingResult().getTarget();
        if (target instanceof TransacaoWrapperDTO dto) {
            dto.getTransacao().getDescricao().setStatus(StatusTransacao.NEGADO.name());

            return ResponseEntity
                    .status(ex.getStatusCode())
                    .header("Erros encontrados", String.join(" | ", erros))
                    .body(dto);
        }

        return ResponseEntity.status(ex.getStatusCode()).build();
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormat(NumberFormatException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro de input: " + ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraint(ConstraintViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Erro de validação: " + ex.getMessage());
    }

}
