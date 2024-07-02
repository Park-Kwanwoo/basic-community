package com.gwan.blog.exception;

import org.springframework.http.HttpStatus;

public class BoardNotFound extends BlogException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public BoardNotFound() {
        super(MESSAGE);
    }

    public BoardNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
