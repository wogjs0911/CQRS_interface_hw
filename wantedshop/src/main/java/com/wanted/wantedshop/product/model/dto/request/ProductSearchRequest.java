package com.wanted.wantedshop.product.model.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductSearchRequest {
    private Long categoryId;
    private Long sellerId;
    private Long brandId;
    private List<Long> tagIds;
    private int page;
    private int perPage;
    private String sort;
    private String status;
    private Integer minPrice;
    private Integer maxPrice;
    private Boolean inStock;
    private String search;
}
