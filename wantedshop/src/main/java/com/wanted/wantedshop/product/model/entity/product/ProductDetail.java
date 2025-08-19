package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.AdditionalInfo;
import com.wanted.wantedshop.product.model.dto.request.DimensionsInfo;
import com.wanted.wantedshop.product.model.dto.request.ProductDetailDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

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

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    private BigDecimal weight;

    /* VO(불변객체)를 만들기 위해서 @Embedded를 사용해야하는데 해당 JSON 구조가 DB에도 같은 구조의 칼럼이 존재해야 한다.
       입력 값이 JSON 구조라면 @Embedded 대신해서 @JdbcTypeCode(SqlTypes.JSON)를 사용해야 한다. */
    @JdbcTypeCode(SqlTypes.JSON)
    private DimensionsInfo dimensions;

    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;

    @JdbcTypeCode(SqlTypes.JSON)
    private AdditionalInfo additionalInfo; // VO로 수정

    public static ProductDetail of(Product product, ProductDetailDto dto) {
        return ProductDetail.builder()
                .product(product)
                .weight(dto.getWeight())
                .dimensions(new DimensionsInfo(
                        dto.getDimensions().getDepth(),
                        dto.getDimensions().getWidth(),
                        dto.getDimensions().getHeight())
                )
                .materials(dto.getMaterials())
                .countryOfOrigin(dto.getCountryOfOrigin())
                .warrantyInfo(dto.getWarrantyInfo())
                .careInstructions(dto.getCareInstructions())
                .additionalInfo(new AdditionalInfo(
                        dto.getAdditionalInfo().getAssemblyRequired(),
                        dto.getAdditionalInfo().getAssemblyTime())
                )
                .build();
    }

    /* 이렇게 하는 이유 : 객체의 직접 참조 대입은 얕은 복사라서 깊은 복사를 위해 VO로 만들어 진행 */
    public void update(ProductDetailDto dto) {
        this.weight = dto.getWeight();
        this.dimensions = new DimensionsInfo(
                        dto.getDimensions().getDepth(),
                        dto.getDimensions().getWidth(),
                        dto.getDimensions().getWidth()
        );
        this.materials = dto.getMaterials();
        this.countryOfOrigin = dto.getCountryOfOrigin();
        this.warrantyInfo = dto.getWarrantyInfo();
        this.careInstructions = dto.getCareInstructions();
        this.additionalInfo = new AdditionalInfo(
                        dto.getAdditionalInfo().getAssemblyRequired(),
                        dto.getAdditionalInfo().getAssemblyTime()
        );
    }
}
