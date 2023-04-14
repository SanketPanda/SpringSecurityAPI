package com.sanket.security.service.post.exception;

import com.sanket.security.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException  extends BaseServiceException {

    private static final long serialVersionUID = -1384505816569861595L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "Post not found with id: %s.";

    public PostNotFoundException(final Long postId) {
        super(String.format(errorMessage, postId));
    }

    public PostNotFoundException(String errorMessage, final Long userId){
        super(String.format(errorMessage, userId));
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}