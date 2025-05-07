package com.wanted.wantedshop.product.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDetailDto {
    private BigDecimal weight;
    private DimensionsInfo dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private AdditionalInfo additionalInfo;
}
