package com.wanted.wantedshop.product.model.dto.request;

import lombok.*;

import java.util.List;

@Setter
@Getter // 또는 @Getter @Setter 조합
@NoArgsConstructor
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
