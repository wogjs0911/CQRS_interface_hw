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
// "images": [
//   {
//     "url": "https://example.com/images/sofa1.jpg",
//     "alt_text": "브라운 소파 정면",
//     "is_primary": true,
//     "display_order": 1,
//     "option_id": null
//   },
//   {
//     "url": "https://example.com/images/sofa2.jpg",
//     "alt_text": "브라운 소파 측면",
//     "is_primary": false,
//     "display_order": 2,
//     "option_id": null
//   }
// ],
}
