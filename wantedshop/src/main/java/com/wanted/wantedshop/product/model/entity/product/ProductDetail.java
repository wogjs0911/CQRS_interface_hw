package com.wanted.wantedshop.product.model.entity.product;

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
    private Map<String, Object> dimensions;

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
    private Map<String, Object> additionalInfo;
}
