package com.wanted.wantedshop.product.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductImageDto {
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long optionId;
}
