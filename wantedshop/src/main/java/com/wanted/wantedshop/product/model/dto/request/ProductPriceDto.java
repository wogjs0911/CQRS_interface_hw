package com.wanted.wantedshop.product.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductPriceDto {
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency;
    private BigDecimal taxRate;
}
