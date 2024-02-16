package com.gwan.blog.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final List<ValidationTuple> validationTuples = new ArrayList<>();

    public void addValidation(FieldError fieldError) {

        ValidationTuple validationTuple = ValidationTuple.builder()
                .fieldName(fieldError.getField())
                .errorMessage(fieldError.getDefaultMessage())
                .build();

        validationTuples.add(validationTuple);
    }

    @RequiredArgsConstructor
    @Getter
    @Builder
    private static class ValidationTuple {

        private final String fieldName;
        private final String errorMessage;

    }
}
