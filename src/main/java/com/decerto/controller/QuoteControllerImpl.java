package com.decerto.controller;

import com.decerto.annotation.LogEntryExit;
import com.decerto.exception.ValidationException;
import com.decerto.model.Quote;
import com.decerto.repository.QuoteRepository;
import com.decerto.service.dto.QuoteDTO;
import com.decerto.service.mapper.QuoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.decerto.service.ValidateService.validate;

@Component
@RequiredArgsConstructor
public class QuoteControllerImpl implements QuoteController {
    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;

    @Override
    @Transactional
    @LogEntryExit(showResult = true)
    public List<QuoteDTO> getQuotes(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Quote> quotePage = quoteRepository.findAll(paging);
        if (quotePage.hasContent()) {
            return quotePage.getContent().stream()
                    .map(quoteMapper::convert)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    @LogEntryExit
    public void addQuotes(QuoteDTO quoteDTO) {
        validate(quoteDTO);
        Quote convert = quoteMapper.convert(quoteDTO);
        quoteRepository.save(convert);
    }

    @Override
    @Transactional
    @LogEntryExit(showResult = true)
    public QuoteDTO updateQuotes(Long id, QuoteDTO quoteDTO) {
        validate(quoteDTO);
        Optional<Quote> quote = quoteRepository.findById(id);
        if (quote.isPresent()) {
            quote.get().setContent(quoteDTO.getContent());
            quoteRepository.save(quote.get());
        } else {
            throw new ValidationException.QuoteNotFoundException();
        }
        return quoteDTO;
    }

    @Override
    @Transactional
    @LogEntryExit
    public void removeQuotes(Long id) {
        Optional<Quote> quote = quoteRepository.findById(id);
        if (quote.isPresent()) {
            quoteRepository.delete(quote.get());
        } else {
            throw new ValidationException.QuoteNotFoundException();
        }
    }
}