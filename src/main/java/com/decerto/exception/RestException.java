package com.decerto.exception;

import com.decerto.exception.dto.ErrorDetails;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
abstract class RestException extends RuntimeException {
    private final HttpStatus HTTP_STATUS;
    private final String CODE;
    private final String MESSAGE;
    private List<ErrorDetails> errors;

    public RestException(HttpStatus httpStatus, String code, String message, List<ErrorDetails> errors) {
        this.HTTP_STATUS = httpStatus;
        this.CODE = code;
        this.MESSAGE = message;
        this.errors = errors;
    }

    public RestException(HttpStatus httpStatus, String code, String message) {
        this.HTTP_STATUS = httpStatus;
        this.CODE = code;
        this.MESSAGE = message;
    }
}