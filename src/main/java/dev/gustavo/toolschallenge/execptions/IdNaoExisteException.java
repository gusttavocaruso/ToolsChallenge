package dev.gustavo.toolschallenge.execptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdNaoExisteException extends RuntimeException {


    public IdNaoExisteException(String id) {
        super(String.format("ID %s nao encontrado", id));
    }
}