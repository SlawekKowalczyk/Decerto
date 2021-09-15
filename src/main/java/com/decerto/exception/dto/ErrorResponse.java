package com.decerto.exception.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String msg;
    private List<ErrorDetails> errors;

    public ErrorResponse(String msg, List<ErrorDetails> errors) {
        this.msg = msg;
        this.errors = errors;
    }

    public ErrorResponse(String msg) {
        this.msg = msg;
    }
}