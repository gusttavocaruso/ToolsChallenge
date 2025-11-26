package dev.gustavo.toolschallenge.execptions;

import dev.gustavo.toolschallenge.domain.enums.StatusTransacao;
import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UniqueIdException.class)
    public ResponseEntity<TransacaoWrapperDTO> handleNegocio(UniqueIdException ex) {

        ex.getDto().getTransacao().getDescricao().setStatus(StatusTransacao.NEGADO.name());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getDto());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TransacaoWrapperDTO> handleValidation(MethodArgumentNotValidException ex) {

        Object target = ex.getBindingResult().getTarget();
        if (target instanceof TransacaoWrapperDTO dto) {
            dto.getTransacao().getDescricao().setStatus(StatusTransacao.NEGADO.name());

            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(dto);
        }

        return ResponseEntity.status(ex.getStatusCode()).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraint(ConstraintViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Erro de validação: " + ex.getMessage());
    }

}
