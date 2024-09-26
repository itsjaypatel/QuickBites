package com.itsjaypatel.quickbites.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDto {

    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = 10;

    @Builder.Default
    private String sortBy = "id";

    @Builder.Default
    private Sort.Direction direction = Sort.Direction.ASC;

    public PageRequest build() {
        return PageRequest.
                of(this.getPageNo() - 1, this.getPageSize(), this.getDirection(), this.getSortBy());
    }
}
