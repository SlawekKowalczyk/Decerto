package com.decerto.service;

import com.decerto.exception.ValidationException;
import com.decerto.exception.dto.ErrorDetails;
import com.decerto.service.dto.QuoteDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.decerto.enums.Fields.*;
import static com.decerto.service.Constant.FIELD_IS_EMPTY;
import static com.decerto.service.Constant.FIELD_IS_REQUIRED;
import static org.springframework.util.StringUtils.isEmpty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateService {

    public static void validate(QuoteDTO quoteDTO) {
        List<ErrorDetails> errors = new ArrayList<>();
        if (quoteDTO == null) {
            errors.add(new ErrorDetails(QUOTE, FIELD_IS_REQUIRED));
        } else if (isEmpty(quoteDTO.getContent())) {
            errors.add(new ErrorDetails(CONTENT, FIELD_IS_EMPTY));
        } else if (isEmpty(quoteDTO.getName())) {
            errors.add(new ErrorDetails(AUTHOR_NAME, FIELD_IS_EMPTY));
        } else if (isEmpty(quoteDTO.getSurname())) {
            errors.add(new ErrorDetails(AUTHOR_SURNAME, FIELD_IS_EMPTY));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException.FieldNotFoundException(errors);
        }
    }
}