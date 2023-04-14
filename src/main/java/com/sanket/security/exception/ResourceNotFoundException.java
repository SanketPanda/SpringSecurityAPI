package com.sanket.security.exception;

import org.springframework.http.HttpStatus;

public abstract class ResourceNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -7130459982757542142L;

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){return status;};
}
