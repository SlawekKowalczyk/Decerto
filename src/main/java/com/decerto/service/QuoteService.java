package com.decerto.service;

import com.decerto.exception.ValidationError;
import com.decerto.exception.ValidationException;
import com.decerto.model.Quote;
import com.decerto.repository.QuoteRepository;
import com.decerto.service.dto.QuoteDTO;
import com.decerto.service.mapper.QuoteMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.decerto.exception.ValidationErrorCode.*;
import static com.decerto.service.Constant.*;
import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@Service
@AllArgsConstructor
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final ObjectMapper objectMapper;
    private final QuoteMapper quoteMapper;

    @Transactional
    public ResponseEntity<List<QuoteDTO>> getQuotes() {
        List<QuoteDTO> collect = quoteRepository.findAll()
                .stream().map(quoteMapper::convert)
                .collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<QuoteDTO> addQuotes(QuoteDTO quoteDTO) {
        log.info(logObjectAsString(quoteDTO));
        validate(quoteDTO);
        Quote convert = quoteMapper.convert(quoteDTO);
        quoteRepository.save(convert);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SneakyThrows
    private String logObjectAsString(QuoteDTO quoteDTO) {
        return objectMapper.writeValueAsString(quoteDTO);
    }

    private void validate(QuoteDTO quoteDTO) {
        List<ValidationError> errors = new ArrayList<>();
        if (quoteDTO == null) {
            errors.add(new ValidationError(QUOTE, FIELD_IS_REQUIRED));
        } else if (quoteDTO.getAuthor() == null) {
            errors.add(new ValidationError(AUTHOR, FIELD_IS_REQUIRED));
        } else if (isEmpty(quoteDTO.getContent())) {
            errors.add(new ValidationError(CONTENT, FIELD_IS_EMPTY));
        } else if (isEmpty(quoteDTO.getAuthor().getName())) {
            errors.add(new ValidationError(AUTHOR_NAME, FIELD_IS_EMPTY));
        } else if (isEmpty(quoteDTO.getAuthor().getSurname())) {
            errors.add(new ValidationError(AUTHOR_SURNAME, FIELD_IS_EMPTY));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Validation errors", errors);
        }
    }

    @Transactional
    public ResponseEntity<Quote> updateQuotes(Long id, QuoteDTO quoteDTO) {
        log.info("id [" + id + "], " + logObjectAsString(quoteDTO));
        validate(quoteDTO);
        Optional<Quote> quote = quoteRepository.findById(id);
        if (quote.isPresent()) {
            quote.get().setContent(quoteDTO.getContent());
            quoteRepository.save(quote.get());
        } else {
            throw new IllegalArgumentException(QUOTE_NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Quote> removeQuotes(Long id) {
        log.info("Remove quote by id: [" + id + "]");
        Optional<Quote> quote = quoteRepository.findById(id);
        if (quote.isPresent()) {
            quoteRepository.delete(quote.get());
        } else {
            throw new IllegalArgumentException(QUOTE_NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}