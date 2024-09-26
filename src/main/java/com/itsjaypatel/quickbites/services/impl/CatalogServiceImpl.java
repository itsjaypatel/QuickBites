package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.dtos.SearchRequestDto;
import com.itsjaypatel.quickbites.entities.FoodItem;
import com.itsjaypatel.quickbites.repositories.FoodItemRepository;
import com.itsjaypatel.quickbites.services.CatalogService;
import com.itsjaypatel.quickbites.services.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final FoodItemRepository foodItemRepository;
    private final SpecificationService specificationService;
    private final ModelMapper modelMapper;

    @Override
    public List<FoodItemDto> searchFood(SearchRequestDto request) {

        Specification<FoodItem> specification = (Specification<FoodItem>) specificationService.build(request);
        return foodItemRepository
                .findAll(specification, request.getPageInfo().build())
                .stream()
                .map(foodItem -> modelMapper.map(foodItem, FoodItemDto.class))
                .collect(Collectors.toList());
    }
}
