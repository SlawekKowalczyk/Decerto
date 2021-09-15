package com.decerto.service.mapper;

import com.decerto.model.Quote;
import com.decerto.service.dto.QuoteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    Quote convert(QuoteDTO quoteDTO);

    QuoteDTO convert(Quote quote);
}