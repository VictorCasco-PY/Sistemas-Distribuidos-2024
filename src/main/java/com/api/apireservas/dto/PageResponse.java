package com.api.apireservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> items;
    private int totalPages;
    private Long totalItems;
    private int currentPage;
}
