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
//     "options": [
//       {
//         "name": "브라운",
//         "additional_price": 0,
//         "sku": "SOFA-BRN",
//         "stock": 10,
//         "display_order": 1
//       },
//       {
//         "name": "블랙",
//         "additional_price": 0,
//         "sku": "SOFA-BLK",
//         "stock": 15,
//         "display_order": 2
//       }
//     ]
}
