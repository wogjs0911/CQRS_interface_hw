package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.ProductDetailDto;
import com.wanted.wantedshop.product.model.dto.request.ProductImageDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_images")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "url", nullable = false)
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "option_id")
    private ProductOption option;

    public static ProductImage of(Long productId, ProductImageDto dto) {
        return ProductImage.builder()
                .product(Product.ofId(productId))
                .url(dto.getUrl())
                .altText(dto.getAltText())
                .isPrimary(dto.getIsPrimary())
                .displayOrder(dto.getDisplayOrder())
                .option(ProductOption.ofId(dto.getOptionId()))
                .build();
    }
}
