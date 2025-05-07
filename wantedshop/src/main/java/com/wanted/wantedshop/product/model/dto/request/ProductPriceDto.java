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

// "price": {
//   "base_price": 599000,
//   "sale_price": 499000,
//   "cost_price": 350000,
//   "currency": "KRW",
//   "tax_rate": 10
// },
}
