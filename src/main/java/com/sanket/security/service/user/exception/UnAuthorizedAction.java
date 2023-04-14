package com.sanket.security.service.user.exception;

import com.sanket.security.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class UnAuthorizedAction extends BaseServiceException {

    private static final long serialVersionUID = -2342019038811684007L;

    private final HttpStatus status = HttpStatus.UNAUTHORIZED;

    public UnAuthorizedAction(String exception) {
        super(exception);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}