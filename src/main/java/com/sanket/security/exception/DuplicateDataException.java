package com.sanket.security.exception;

import org.springframework.http.HttpStatus;

public abstract class DuplicateDataException extends BaseServiceException {

    private static final long serialVersionUID = 5781786879118983650L;

    private final HttpStatus status = HttpStatus.CONFLICT;

    public DuplicateDataException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){return status;};
}