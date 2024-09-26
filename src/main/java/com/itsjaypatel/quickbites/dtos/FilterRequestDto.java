package com.itsjaypatel.quickbites.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterRequestDto {

    private String column;

    private String value;

    @Builder.Default
    private Operator comparisonOperator = Operator.LIKE;

    public enum Operator {
        EQUAL, BETWEEN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL, LIKE, IN
    }
}

