package com.sanket.security.service.user.exception;

import com.sanket.security.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class UserAccountNotVerifiedException extends BaseServiceException {

    private static final long serialVersionUID = -2342019038811684007L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "User account is not verified.";

    public UserAccountNotVerifiedException() {
        super(errorMessage);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
