package com.wanted.wantedshop.product.model.dto.response;

import com.wanted.wantedshop.common.exception.BaseResDto;
import com.wanted.wantedshop.product.model.entity.product.Brand;
import com.wanted.wantedshop.product.model.entity.product.ProductImage;
import com.wanted.wantedshop.product.model.entity.product.Seller;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Setter
@Getter // 또는 @Getter @Setter 조합
@NoArgsConstructor
@SuperBuilder
public class ProductSearchResponse extends BaseResDto {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private Seller seller;
    private String brandName;
    private String primaryImage;
    private BigDecimal rating;
    private Integer reviewCount;
    private Boolean inStock;
    private String status;

    // QueryDSL 생성자
    public ProductSearchResponse(Long id, String name, String slug, String shortDescription,
                                 BigDecimal basePrice, BigDecimal salePrice, String primaryImage,
                                 String brandName, Double rating) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.primaryImage = primaryImage;
        this.brandName = brandName;
        this.rating = BigDecimal.valueOf(rating);  // rating은 BigDecimal로 변환
    }
}
