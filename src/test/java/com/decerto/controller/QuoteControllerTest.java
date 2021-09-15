package com.decerto.controller;

import com.decerto.exception.ValidationException;
import com.decerto.model.Quote;
import com.decerto.repository.QuoteRepository;
import com.decerto.service.dto.AuthorDto;
import com.decerto.service.dto.QuoteDTO;
import com.decerto.service.mapper.QuoteMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    private final String contentPL = "Źli programiści martwią się o kod. Dobrzy programiści martwią się strukturami danych i ich relacjami.";
    private final String contentENG = "Bad developers worry about the code. Good developer's concerned about the data structure and their relationships.";
    private final String quotePath = "/api/v1/quote";

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
        List<Quote> quotes = Collections.singletonList(quoteMapper.convert(getQuoteDto()));
        Mockito.when(quoteRepository.findAll()).thenReturn(quotes);
        mockMvc.perform(get(quotePath + "/")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(contentPL))
                .andExpect(jsonPath("$[0].author.name").value("Linus"))
                .andExpect(jsonPath("$[0].author.surname").value("Torvalds"));
    }

    @Test
    void addQuotes() throws Exception {
        mockMvc.perform(post(quotePath + "/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(getQuoteDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void ValidationExceptionTesting() throws Exception {
        QuoteDTO quote = getQuoteDto();
        quote.getAuthor().setName("");
        mockMvc.perform(post(quotePath + "/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(quote)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException))
                .andExpect(result -> assertEquals("Validation errors", result.getResolvedException().getMessage()));
    }

    @Test
    void updateQuotes() throws Exception {
        Mockito.when(quoteRepository.findById(1L)).thenReturn(Optional.of(quoteMapper.convert(getQuoteDto())));

        mockMvc.perform(put(quotePath + "/{id}", 1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(updateQuote())))
                .andExpect(status().isOk());

        assertEquals(contentENG, quoteRepository.findById(1L).get().getContent());
    }

    @Test
    void removeQuotes() throws Exception {
        Mockito.when(quoteRepository.findById(1L)).thenReturn(Optional.of(quoteMapper.convert(getQuoteDto())));

        mockMvc.perform(delete(quotePath + "/{id}", 1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(getQuoteDto())))
                .andExpect(status().isOk());
    }

    private QuoteDTO getQuoteDto() {
        QuoteDTO quote = new QuoteDTO();
        AuthorDto author = new AuthorDto();
        author.setName("Linus");
        author.setSurname("Torvalds");
        quote.setAuthor(author);
        quote.setContent(contentPL);
        return quote;
    }

    private QuoteDTO updateQuote() {
        QuoteDTO quoteDto = getQuoteDto();
        quoteDto.setContent(contentENG);
        return quoteDto;
    }
}