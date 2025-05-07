package com.wanted.wantedshop.product.model.entity.category;

import com.wanted.wantedshop.product.model.dto.request.ProductPriceDto;
import com.wanted.wantedshop.product.model.entity.product.Product;
import com.wanted.wantedshop.product.model.entity.product.ProductCategory;
import com.wanted.wantedshop.product.model.entity.product.ProductPrice;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentId;

    @Size(max = 100)
    @NotNull
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(nullable = false, length = 100)
    private String slug;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    @NotNull
    @Column(nullable = false)
    private Integer level;

    @Size(max = 255)
    private String imageUrl;

    @OneToMany(mappedBy = "category")
    private List<ProductCategory> productCategories = new ArrayList<>();

    public static Category ofId(Long categoryId) {
        return categoryId == null ? null : Category.builder().id(categoryId).build();
    }
}
