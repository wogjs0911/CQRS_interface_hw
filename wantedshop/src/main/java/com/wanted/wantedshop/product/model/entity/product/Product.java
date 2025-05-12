package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.common.BaseEntity;
import com.wanted.wantedshop.product.model.dto.request.ProductSaveRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String slug;
    private String shortDescription;
    private String fullDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductCategory> categories = new ArrayList<>();

    public static Product ofId(Long id) {
        return id != null ? Product.builder().id(id).build() : null;
    }

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

    public void update(ProductSaveRequest saveRequest) {
        this.name = saveRequest.getName();
        this.slug = saveRequest.getSlug();
        this.fullDescription = saveRequest.getFullDescription();
        this.shortDescription = saveRequest.getShortDescription();
        this.seller = Seller.ofId(saveRequest.getSellerId());
        this.brand = Brand.ofId(saveRequest.getBrandId());
        this.status = saveRequest.getStatus();
    }
}
