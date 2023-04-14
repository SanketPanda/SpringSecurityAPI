package com.sanket.security.service.user.exception;

import com.sanket.security.exception.ResourceNotFoundException;

public class UserIdRequiredException extends ResourceNotFoundException {

    private static final long serialVersionUID = -2712734477330580341L;

    private static final String errorMessage = "User id is required while update.";

    public UserIdRequiredException() {
        super(errorMessage);
    }
}
