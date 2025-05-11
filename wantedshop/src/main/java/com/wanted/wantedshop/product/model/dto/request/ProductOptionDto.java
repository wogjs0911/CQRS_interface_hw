package com.wanted.wantedshop.product.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductOptionDto {
    private String name;
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}
