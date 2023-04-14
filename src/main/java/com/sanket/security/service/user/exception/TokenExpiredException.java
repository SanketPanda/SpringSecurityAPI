package com.sanket.security.service.user.exception;

import com.sanket.security.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException  extends BaseServiceException {

    private static final long serialVersionUID = -9170254420689080151L;

    private final HttpStatus status = HttpStatus.UNAUTHORIZED;

    private static final String errorMessage = "The token is expired please request to resend the verification token!";

    public TokenExpiredException() {
        super(errorMessage);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}