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

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductDetail detail;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductPrice price;

//    // Category를 ManyToMany 관계로 직접 참조 -> 원래 ManyToMany에서 이런게 존재해야되는데 ManyToMany는 실무에 적합하지 않음
//    ** 따라서, ProductCategory와 같은 중간테이블 생성
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "product_categories",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id")
//    )
    // ** List같은 Collection 경우 Builder.Default가 필요하다
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductOptionGroup> optionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

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

    public void updateProductEntity(ProductSaveRequest dto) {
        this.name = dto.getName();
        this.slug = dto.getSlug();
        this.fullDescription = dto.getFullDescription();
        this.shortDescription = dto.getShortDescription();
        this.status = dto.getStatus();
    }
}
