package com.javamentor.developer.social.platform.models.dto.page;

import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationException;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class PageDto <T, V> implements Serializable {
    private int currentPage;
    private int totalPages;
    private int itemsOnPage;
    private Long totalResults;
    private List <T> items;
    private List <V> meta;


    public PageDto(int currentPage, int itemsOnPage, List<T> items, Long totalResults) throws IllegalArgumentException {
        this.currentPage = currentPage;
        this.itemsOnPage = itemsOnPage;
        this.totalResults = totalResults;
        this.items = items;

        if(totalResults != 0){
            if(totalResults % itemsOnPage == 0){
                totalPages = (int) (totalResults / itemsOnPage);
            } else {
                totalPages = (int) (totalResults / itemsOnPage) + 1;
            }
        } else {
            totalPages = 0;
        }

        if (currentPage > totalPages) {
            throw new IllegalArgumentException("Nonexistent page construction. " +
                    String.format("Parameter 'currentPage' value [%d] is greater than total number of available pages [%d] " +
                            "considering parameter 'itemsOnPage' value [%d]", this.currentPage, totalPages, this.itemsOnPage));
        }
    }
}
