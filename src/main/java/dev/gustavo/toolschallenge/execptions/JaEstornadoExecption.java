package dev.gustavo.toolschallenge.execptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JaEstornadoExecption extends RuntimeException {

    public JaEstornadoExecption(String id) {
        super(String.format("Transacao de ID %s ja foi estornada", id));
    }

}