package com.gwan.blog.exception;

import org.springframework.http.HttpStatus;

public abstract class BlogException extends RuntimeException {

    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getStatusCode();

}
