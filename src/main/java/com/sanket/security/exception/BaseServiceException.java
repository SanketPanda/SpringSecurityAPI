package com.sanket.security.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseServiceException extends RuntimeException{

    private static final long serialVersionUID = 7215484939692080443L;

    public abstract HttpStatus getStatus();

    public BaseServiceException(){}

    public BaseServiceException(String message){super(message);}

    public BaseServiceException(Throwable cause){super(cause);}

    public BaseServiceException(String message, Throwable cause){super(message, cause);}
}
