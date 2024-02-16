package com.gwan.blog.controller;

import com.gwan.blog.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class BlogRestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse InvalidExceptionHandler(MethodArgumentNotValidException exception) {

        List<FieldError> fieldErrors = exception.getFieldErrors();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("오류 발생")
                .build();

        for (FieldError fieldError : fieldErrors) {
            errorResponse.addValidation(fieldError);
        }

        return errorResponse;
    }
}
