package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.ProductPriceDto;
import jakarta.persistence.*;
import lombok.*;
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

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency;
    private BigDecimal taxRate;

    public static ProductPrice of(Product product, ProductPriceDto dto) {
        return ProductPrice.builder()
                .product(product)
                .basePrice(dto.getBasePrice())
                .salePrice(dto.getSalePrice())
                .costPrice(dto.getCostPrice())
                .currency(dto.getCurrency())
                .taxRate(dto.getTaxRate())
                .build();
    }

    public void update(ProductPriceDto dto) {
        this.basePrice = dto.getBasePrice();
        this.salePrice = dto.getSalePrice();
        this.costPrice = dto.getCostPrice();
        this.currency = dto.getCurrency();
        this.taxRate = dto.getTaxRate();
    }
}
