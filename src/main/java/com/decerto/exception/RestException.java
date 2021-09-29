package com.decerto.exception;

import com.decerto.exception.dto.ErrorDetails;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
abstract class RestException extends RuntimeException {
    private HttpStatus httpStatus;
    private String code;
    private String message;
    private List<ErrorDetails> errors;

    public RestException(HttpStatus httpStatus, String code, String message, List<ErrorDetails> errors) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public RestException(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}