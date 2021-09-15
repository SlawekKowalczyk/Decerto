package com.decerto.exception.dto;

import com.decerto.enums.Fields;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDetails {
    private final Fields field;
    private final String msg;
}