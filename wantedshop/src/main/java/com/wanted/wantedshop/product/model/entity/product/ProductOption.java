package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.ProductOptionDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "product_options")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductOption {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    @Column(nullable = false) private String name;
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;

    public static ProductOption ofId(Long optionId) {
        return optionId == null ? null : ProductOption.builder().id(optionId).build();
    }

    public static ProductOption of(ProductOptionGroup optionGroup, ProductOptionDto dto) {
        return ProductOption.builder()
                .optionGroup(optionGroup)
                .name(dto.getName())
                .additionalPrice(dto.getAdditionalPrice())
                .sku(dto.getSku())
                .stock(dto.getStock())
                .displayOrder(dto.getDisplayOrder())
                .build();
    }
}
