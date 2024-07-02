package com.gwan.blog.exception;

import org.springframework.http.HttpStatus;

public class InvalidSignInInformation extends BlogException {

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidSignInInformation() {
        super(MESSAGE);
    }

    public InvalidSignInInformation(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
