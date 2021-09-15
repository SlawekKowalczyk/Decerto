package com.decerto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
class ErrorResponse {

    private String msg;
    private List<ValidationError> errors;

    ErrorResponse(String msg) {
        this.msg = msg;
    }
}