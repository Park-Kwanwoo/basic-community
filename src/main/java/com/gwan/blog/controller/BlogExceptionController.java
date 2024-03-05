package com.gwan.blog.controller;

import com.gwan.blog.exception.BlogException;
import com.gwan.blog.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class BlogExceptionController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse ValidExceptionHandler(MethodArgumentNotValidException e) {

        List<FieldError> errors = e.getFieldErrors();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError error : errors) {
            errorResponse.addValidation(error);
        }

        return errorResponse;
    }

    @ResponseBody
    @ExceptionHandler(BlogException.class)
    public ResponseEntity<ErrorResponse> BlogExceptionHandler(BlogException e) {

        String message = e.getMessage();

        ErrorResponse error = ErrorResponse.builder()
                .code(e.getStatusCode().getReasonPhrase())
                .message(message)
                .build();

        ResponseEntity<ErrorResponse> errorResponse = ResponseEntity
                .status(e.getStatusCode())
                .body(error);

        return errorResponse;
    }
}
