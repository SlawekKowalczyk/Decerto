package com.decerto.controller;

import com.decerto.model.Quote;
import com.decerto.service.QuoteService;
import com.decerto.service.dto.QuoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuoteControllerImpl implements QuoteController {
    private final QuoteService quoteService;

    @Override
    public ResponseEntity<List<QuoteDTO>> getQuotes() {
        return quoteService.getQuotes();
    }

    @Override
    public ResponseEntity<QuoteDTO> addQuotes(QuoteDTO quoteDTO) {
        return quoteService.addQuotes(quoteDTO);
    }

    @Override
    public ResponseEntity<Quote> updateQuotes(Long id, QuoteDTO quoteDTO) {
        return quoteService.updateQuotes(id, quoteDTO);
    }

    @Override
    public ResponseEntity<Quote> removeQuotes(Long id) {
        return quoteService.removeQuotes(id);
    }
}