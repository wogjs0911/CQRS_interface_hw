package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.ProductDetailDto;
import com.wanted.wantedshop.product.model.dto.request.ProductPriceDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "product_prices")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency;
    private BigDecimal taxRate;

    public static ProductPrice of(Long productId, ProductPriceDto dto) {
        return ProductPrice.builder()
                .product(Product.ofId(productId))
                .basePrice(dto.getBasePrice())
                .salePrice(dto.getSalePrice())
                .costPrice(dto.getCostPrice())
                .currency(dto.getCurrency())
                .taxRate(dto.getTaxRate())
                .build();
    }
}
