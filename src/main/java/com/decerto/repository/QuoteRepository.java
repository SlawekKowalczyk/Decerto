package com.decerto.repository;

import com.decerto.model.Quote;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long> {
}