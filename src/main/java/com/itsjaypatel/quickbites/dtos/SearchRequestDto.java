package com.itsjaypatel.quickbites.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequestDto {

    @Builder.Default
    private List<FilterRequestDto> filters = new ArrayList<>();

    @Builder.Default
    private GlobalOperation globalOperation = GlobalOperation.AND;

    @Builder.Default
    private PageRequestDto pageInfo = new PageRequestDto(1, 10, "id", Sort.Direction.ASC);

    public enum GlobalOperation {
        AND, OR, NOT
    }


}
