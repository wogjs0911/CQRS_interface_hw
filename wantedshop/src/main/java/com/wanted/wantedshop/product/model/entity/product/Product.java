package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.common.exception.BaseResDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseResDto {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(nullable = false)
    private String slug;

    @Size(max = 500)
    @Column(length = 500)
    private String shortDescription;

    @Column(length = Integer.MAX_VALUE)
    private String fullDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Size(max = 20)
    @NotNull
    @Column(nullable = false, length = 20)
    private String status;
}
