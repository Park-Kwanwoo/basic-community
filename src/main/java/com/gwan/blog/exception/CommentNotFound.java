package com.gwan.blog.exception;

import org.springframework.http.HttpStatus;

public class CommentNotFound extends BlogException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFound() {
        super(MESSAGE);
    }

    public CommentNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
