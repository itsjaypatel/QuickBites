package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.SearchRequestDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationService {

    Specification<?> build(SearchRequestDto searchRequestDto);
}
