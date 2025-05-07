package com.wanted.wantedshop.product.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCategoryDto {
    private Long categoryId;
    private Boolean isPrimary;

//     "categories": [
//   {
//     "category_id": 5,
//     "is_primary": true
//   },
//   {
//     "category_id": 8,
//     "is_primary": false
//   }
// ],

}
