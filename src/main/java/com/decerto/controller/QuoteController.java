package com.decerto.controller;

import com.decerto.model.Quote;
import com.decerto.service.dto.QuoteDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Quote Controller"})
@RestController
@RequestMapping("/api/v1/quote")
interface QuoteController {

    @ApiOperation(value = "Get all available quotes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return list of quotes"),
    })
    @GetMapping("/")
    ResponseEntity<List<QuoteDTO>> getQuotes();

    @ApiOperation(value = "Add quote")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Quote was saved"),
            @ApiResponse(code = 404, message = "Validation errors")
    })
    @PostMapping("/add")
    ResponseEntity<QuoteDTO> addQuotes(@RequestBody QuoteDTO quoteDTO);

    @ApiOperation(value = "Update existing quote")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Quote was saved"),
            @ApiResponse(code = 404, message = "Validation errors")
    })
    @PutMapping("/{id}")
    ResponseEntity<Quote> updateQuotes(@PathVariable("id") Long id, @RequestBody QuoteDTO quoteDTO);

    @ApiOperation(value = "Delete quote by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Quote was deleted"),
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Quote> removeQuotes(@PathVariable("id") Long id);
}