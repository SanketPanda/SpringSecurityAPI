package com.sanket.security.service.user.exception;

import com.sanket.security.exception.DuplicateDataException;

public class UserAlreadyExistsException extends DuplicateDataException {

    private static final long serialVersionUID = -3042721014458012883L;

    private static final String errorMessage = "User already exist with email: %s.";

    public UserAlreadyExistsException(String message) {
        super(String.format(errorMessage, message));
    }
}
