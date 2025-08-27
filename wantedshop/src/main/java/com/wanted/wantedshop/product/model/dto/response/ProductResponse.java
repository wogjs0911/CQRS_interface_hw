package com.wanted.wantedshop.product.model.dto.response;

import com.wanted.wantedshop.common.BaseEntity;
import com.wanted.wantedshop.product.model.entity.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class ProductResponse extends BaseEntity {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .sellerId(product.getSeller().getId())
                .brandId(product.getBrand().getId())
                .status(String.valueOf(product.getStatus()))
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
