package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.common.BaseEntity;
import com.wanted.wantedshop.common.exception.BaseResDto;
import com.wanted.wantedshop.product.model.dto.request.ProductSaveRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
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
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> categories = new ArrayList<>();

    public static Product from(ProductSaveRequest dto) {
        return Product.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .seller(Seller.ofId(dto.getSellerId()))
                .brand(Brand.ofId(dto.getBrandId()))
                .status(dto.getStatus())
                .build();
    }

    public static Product ofId(Long id) {
        return id != null ? Product.builder().id(id).build() : null;
    }
}
