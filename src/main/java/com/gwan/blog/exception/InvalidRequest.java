package com.gwan.blog.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequest extends BlogException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
