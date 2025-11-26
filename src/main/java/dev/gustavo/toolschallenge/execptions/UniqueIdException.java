package dev.gustavo.toolschallenge.execptions;

import dev.gustavo.toolschallenge.dto.TransacaoWrapperDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UniqueIdException extends RuntimeException {

    private final TransacaoWrapperDTO dto;

    public UniqueIdException(String message, TransacaoWrapperDTO dto) {
        super(message);
        this.dto = dto;
    }

    public TransacaoWrapperDTO getDto() {
        return dto;
    }
}