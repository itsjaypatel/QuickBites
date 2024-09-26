package com.itsjaypatel.quickbites.specifications;

import com.itsjaypatel.quickbites.dtos.FilterRequestDto;
import com.itsjaypatel.quickbites.dtos.SearchRequestDto;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.services.SpecificationService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Override
    public Specification<?> build(SearchRequestDto request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (FilterRequestDto filter : request.getFilters()) {
                switch (filter.getComparisonOperator()) {
                    case EQUAL:
                        Predicate equal = cb.equal(root.get(filter.getColumn()), filter.getValue());
                        predicates.add(equal);
                        break;
                    case IN:
                        String[] split = filter.getValue().split(",");
                        Predicate in = root.get(filter.getColumn()).in(Arrays.asList(split));
                        predicates.add(in);
                        break;
                    case LIKE:
                        Predicate like = cb.like(cb.lower(root.get(filter.getColumn())), String.join("", "%", filter.getValue().toLowerCase(), "%"));
                        predicates.add(like);
                        break;
                    case LESS_THAN_OR_EQUAL:
                        Predicate lessThanOrEqual = cb.lessThanOrEqualTo(root.get(filter.getColumn()), filter.getValue());
                        predicates.add(lessThanOrEqual);
                        break;
                    case GREATER_THAN_OR_EQUAL:
                        Predicate greaterThanOrEqual = cb.greaterThanOrEqualTo(root.get(filter.getColumn()), filter.getValue());
                        predicates.add(greaterThanOrEqual);
                        break;
                    case BETWEEN:
                        String[] values = filter.getValue().split(",");
                        Predicate between = cb.between(root.get(filter.getColumn()), Long.parseLong(values[0]), Long.parseLong(values[1]));
                        predicates.add(between);
                        break;
                    default:
                        throw new BadRequestException("Invalid comparison operator");
                }
            }

            if (request.getGlobalOperation().equals(SearchRequestDto.GlobalOperation.AND)) {
                return cb.and(predicates.toArray(new Predicate[0]));
            } else if (request.getGlobalOperation().equals(SearchRequestDto.GlobalOperation.OR)) {
                return cb.or(predicates.toArray(new Predicate[0]));
            }

            throw new BadRequestException("Unsupported global operation: " + request.getGlobalOperation());
        };
    }
}
