package com.sanket.security.exception;

import org.springframework.http.HttpStatus;

public class GenericException  extends BaseServiceException {

    private static final long serialVersionUID = 7301916218744223850L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    public GenericException(String exception) {
        super(exception);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}