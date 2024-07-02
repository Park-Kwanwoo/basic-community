package com.gwan.blog.exception;

import org.springframework.http.HttpStatus;

public class Unauthorized extends BlogException {

    private final static String MESSAGE = "인증되지 않았습니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    public Unauthorized(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
