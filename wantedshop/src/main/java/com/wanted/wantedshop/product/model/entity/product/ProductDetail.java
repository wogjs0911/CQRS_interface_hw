package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.AdditionalInfo;
import com.wanted.wantedshop.product.model.dto.request.DimensionsInfo;
import com.wanted.wantedshop.product.model.dto.request.ProductDetailDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "product_details")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @JdbcTypeCode(SqlTypes.JSON)
    private DimensionsInfo dimensions;

    @Column(length = Integer.MAX_VALUE)
    private String materials;

    @Size(max = 100)
    @Column(length = 100)
    private String countryOfOrigin;

    @Column(length = Integer.MAX_VALUE)
    private String warrantyInfo;

    @Column(length = Integer.MAX_VALUE)
    private String careInstructions;

    @JdbcTypeCode(SqlTypes.JSON)
    private AdditionalInfo additionalInfo;

    public static ProductDetail of(Long productId, ProductDetailDto dto) {
        return ProductDetail.builder()
                .product(Product.ofId(productId))
                .weight(dto.getWeight())
                .dimensions(dto.getDimensions())
                .materials(dto.getMaterials())
                .countryOfOrigin(dto.getCountryOfOrigin())
                .warrantyInfo(dto.getWarrantyInfo())
                .careInstructions(dto.getCareInstructions())
                .additionalInfo(dto.getAdditionalInfo())
                .build();
    }
}
