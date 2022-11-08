package com.example.demo.catalog.response;

import lombok.Data;

@Data
public class PageResponse {
    private Integer totalItems;
    private Object items;
    private Integer totalPages;
    private Integer currentPage;
}
