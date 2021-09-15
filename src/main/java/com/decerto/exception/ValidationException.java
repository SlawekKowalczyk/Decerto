package com.decerto.exception;

import com.decerto.exception.dto.ErrorDetails;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ValidationException extends RestException {
    private static final String FIELD_NOT_FOUND = "FIELD_NOT_FOUND";
    private static final String QUOTE_NOT_FOUND = "QUOTE_NOT_FOUND";

    public ValidationException(HttpStatus httpStatus, String code, String message) {
        super(httpStatus, code, message);
    }

    public ValidationException(HttpStatus httpStatus, String code, String message, List<ErrorDetails> errors) {
        super(httpStatus, code, message, errors);
    }

    public static class FieldNotFoundException extends ValidationException {
        public FieldNotFoundException(List<ErrorDetails> errors) {
            super(HttpStatus.NOT_FOUND, FIELD_NOT_FOUND, "Field not found", errors);
        }
    }

    public static class QuoteNotFoundException extends ValidationException {
        public QuoteNotFoundException() {
            super(HttpStatus.BAD_REQUEST, QUOTE_NOT_FOUND, "Quote not found");
        }
    }
}