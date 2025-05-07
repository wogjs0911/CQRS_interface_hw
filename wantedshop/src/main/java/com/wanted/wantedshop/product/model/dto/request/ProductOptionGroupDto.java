package com.wanted.wantedshop.product.model.dto.request;

import com.wanted.wantedshop.product.model.entity.product.ProductOption;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductOptionGroupDto {
    private String name;
    private Integer displayOrder;
    private List<ProductOptionDto> options; // JPA 성능 문제 발생 가능성

// "option_groups": [
//   {
//     "name": "색상",
//     "display_order": 1,
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
//   },

}
