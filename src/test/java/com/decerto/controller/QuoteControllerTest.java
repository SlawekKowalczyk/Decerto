package com.decerto.controller;

import com.decerto.exception.ValidationException;
import com.decerto.model.Quote;
import com.decerto.repository.QuoteRepository;
import com.decerto.service.dto.QuoteDTO;
import com.decerto.service.mapper.QuoteMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QuoteControllerTest {
    private final String CONTENT_PL = "Źli programiści martwią się o kod. Dobrzy programiści martwią się strukturami danych i ich relacjami.";
    private final String CONTENT_ENG = "Bad developers worry about the code. Good developer's concerned about the data structure and their relationships.";
    private final String QUOTE_PATH = "/api/v1/quote";

    @MockBean
    private QuoteRepository quoteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuoteMapper quoteMapper;

    @Test
    void getQuotes() throws Exception {
        Mockito.when(quoteRepository.findAll(PageRequest.of(0, 10))).thenReturn(getQuotePage());
        mockMvc.perform(get(QUOTE_PATH + "/")
                .contentType("application/json")
                .param("pageNo", "0")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(CONTENT_PL))
                .andExpect(jsonPath("$[0].name").value("Linus"))
                .andExpect(jsonPath("$[0].surname").value("Torvalds"));
    }

    @Test
    void addQuotes() throws Exception {
        mockMvc.perform(post(QUOTE_PATH + "/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(getQuoteDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void ValidationExceptionTesting() throws Exception {
        QuoteDTO quote = getQuoteDto();
        quote.setName("");
        mockMvc.perform(post(QUOTE_PATH + "/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(quote)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void updateQuotes() throws Exception {
        Mockito.when(quoteRepository.findById(1L)).thenReturn(Optional.of(quoteMapper.convert(getQuoteDto())));

        mockMvc.perform(put(QUOTE_PATH + "/{id}", 1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(updateQuote())))
                .andExpect(status().isOk());

        assertEquals(CONTENT_ENG, quoteRepository.findById(1L).get().getContent());
    }

    @Test
    void removeQuotes() throws Exception {
        Mockito.when(quoteRepository.findById(1L)).thenReturn(Optional.of(quoteMapper.convert(getQuoteDto())));

        mockMvc.perform(delete(QUOTE_PATH + "/{id}", 1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(getQuoteDto())))
                .andExpect(status().isOk());
    }

    private PageImpl getQuotePage() {
        Quote quote = new Quote();
        quote.setName("Linus");
        quote.setSurname("Torvalds");
        quote.setContent(CONTENT_PL);
        List<Quote> companies = Collections.singletonList(quote);
        return new PageImpl(companies);
    }

    private QuoteDTO getQuoteDto() {
        QuoteDTO quote = new QuoteDTO();
        quote.setName("Linus");
        quote.setSurname("Torvalds");
        quote.setContent(CONTENT_PL);
        return quote;
    }

    private QuoteDTO updateQuote() {
        QuoteDTO quoteDto = getQuoteDto();
        quoteDto.setContent(CONTENT_ENG);
        return quoteDto;
    }
}