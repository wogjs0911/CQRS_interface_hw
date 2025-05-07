package com.wanted.wantedshop.product.model.dto.request;

import com.wanted.wantedshop.product.model.entity.product.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ProductSaveRequest {
    @NotNull private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    @NotNull private Long categoryId;
    @NotNull private Long sellerId;
    @NotNull private Long brandId;
    @NotNull private ProductStatus status;

    private ProductDetailDto detail;
    private ProductPriceDto price;
    private ProductCategoryDto categories;
    private ProductOptionDto options;
    private ProductOptionGroupDto optionGroups;
    private ProductImageDto images;
    private ProductTagDto tags;
}
