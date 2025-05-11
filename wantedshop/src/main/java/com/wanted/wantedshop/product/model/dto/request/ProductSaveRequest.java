package com.wanted.wantedshop.product.model.dto.request;

import com.wanted.wantedshop.common.BaseEntity;
import com.wanted.wantedshop.common.exception.BaseResDto;
import com.wanted.wantedshop.product.model.entity.product.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
    private List<ProductCategoryDto> categories;
    private List<ProductOptionGroupDto> optionGroups;
    private List<ProductImageDto> images;
    private List<Long> tags;
}
