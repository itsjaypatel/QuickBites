package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.dtos.SearchRequestDto;

import java.util.List;

public interface CatalogService {

    List<FoodItemDto> searchFood(SearchRequestDto searchRequestDto);
}
