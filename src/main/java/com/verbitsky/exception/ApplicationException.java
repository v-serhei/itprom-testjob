package com.verbitsky.exception;

import java.io.Serial;

public class ApplicationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4288082219802464408L;

    public ApplicationException(String message) {
        super(message);
    }
}
