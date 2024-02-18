package com.verbitsky.exception;

import java.io.Serial;

public class RepositoryException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 9098268356213140799L;

    public RepositoryException(String message) {
        super(message);
    }
}
