package com.decerto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class ValidationError {
    private ValidationErrorCode field;
    private String message;
}
