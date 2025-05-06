package com.wanted.wantedshop.product.model.dto.response;

import com.wanted.wantedshop.common.exception.BaseResDto;
import com.wanted.wantedshop.product.model.entity.product.Brand;
import com.wanted.wantedshop.product.model.entity.product.ProductImage;
import com.wanted.wantedshop.product.model.entity.product.Seller;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
public class ProductSearchResponse extends BaseResDto {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String basePrice;
    private String salePrice;
    private Seller seller;
    private Brand brand;
    private ProductImage primaryImage;
    private BigDecimal rating;
    private Integer reviewCount;
    private Boolean inStock;
    private String status;
}
