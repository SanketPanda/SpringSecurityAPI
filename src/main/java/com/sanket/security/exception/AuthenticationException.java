package com.sanket.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

public abstract class AuthenticationException extends AccessDeniedException {

    private static final long serialVersionUID = 7215484939692080443L;

    public abstract HttpStatus getStatus();

    public abstract String getErrorCode();

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
