package com.decerto.exception.handler;

import com.decerto.exception.ValidationException;
import com.decerto.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ValidationException.FieldNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(ValidationException.FieldNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), ex.getErrors());
    }

    @ExceptionHandler(ValidationException.QuoteNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse wrongParameter(ValidationException.QuoteNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse internal(Throwable ex) {
        return new ErrorResponse(ex.getLocalizedMessage());
    }
}