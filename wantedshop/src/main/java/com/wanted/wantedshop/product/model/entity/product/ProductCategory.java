package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.*;
import com.wanted.wantedshop.product.model.entity.category.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_categories")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id")
    private Category category;

    private Boolean isPrimary;

    public static ProductCategory of(Long productId, ProductCategoryDto dto) {
        return ProductCategory.builder()
                .product(Product.ofId(productId))
                .category(Category.ofId(dto.getCategoryId()))
                .isPrimary(dto.getIsPrimary())
                .build();
    }

    public void update(ProductCategoryDto dto) {
        this.category = Category.ofId(dto.getCategoryId());
        this.isPrimary = dto.getIsPrimary();
    }
}
