package com.sanket.security.service.user.exception;

import com.sanket.security.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -2342019038811684007L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "User not found with email: %s.";

    public UserNotFoundException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public UserNotFoundException(String errorMessage, final Long userId){
        super(String.format(errorMessage, userId));
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
