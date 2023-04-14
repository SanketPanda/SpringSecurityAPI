package com.sanket.security.service.user.exception;

import com.sanket.security.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class InvalidConfirmationTokenException extends BaseServiceException {

    private static final long serialVersionUID = 8708097266597485854L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "The token is invalid or link is broken!";

    public InvalidConfirmationTokenException() {
        super(errorMessage);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}