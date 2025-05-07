package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.ProductOptionDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

    @Size(max = 100)
    @NotNull
    @Column(nullable = false, length = 100)
    private String name;

    @ColumnDefault("0")
    @Column(precision = 12, scale = 2)
    private BigDecimal additionalPrice;

    @Size(max = 100)
    @Column(length = 100)
    private String sku;

    @ColumnDefault("0")
    private Integer stock;

    @ColumnDefault("0")
    private Integer displayOrder;

    public static ProductOption ofId(Long optionId) {
        return optionId == null ? null : ProductOption.builder().id(optionId).build();
    }

    public static ProductOption from(ProductOptionDto dto) {
        return ProductOption.builder()
                .name(dto.getName())
                .additionalPrice(dto.getAdditionalPrice())
                .sku(dto.getSku())
                .stock(dto.getStock())
                .displayOrder(dto.getDisplayOrder())
                .build();
    }
}
