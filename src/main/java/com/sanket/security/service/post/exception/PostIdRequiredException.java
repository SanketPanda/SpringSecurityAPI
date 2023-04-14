package com.sanket.security.service.post.exception;

import com.sanket.security.exception.ResourceNotFoundException;

public class PostIdRequiredException  extends ResourceNotFoundException {

    private static final long serialVersionUID = -2712734477330580341L;

    private static final String errorMessage = "Post id is required while update.";

    public PostIdRequiredException() {
        super(errorMessage);
    }
}
