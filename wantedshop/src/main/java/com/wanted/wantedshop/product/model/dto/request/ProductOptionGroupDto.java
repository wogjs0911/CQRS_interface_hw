package com.wanted.wantedshop.product.model.dto.request;

import com.wanted.wantedshop.product.model.entity.product.ProductOption;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductOptionGroupDto {
    private Long id;
    private String name;
    private Integer displayOrder;
    private List<ProductOptionDto> options;
}
