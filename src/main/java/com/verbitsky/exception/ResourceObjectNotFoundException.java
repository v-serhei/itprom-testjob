package com.verbitsky.exception;

import java.io.Serial;

public class ResourceObjectNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1191963479981576081L;

    public ResourceObjectNotFoundException(String message) {
        super(message);
    }
}
