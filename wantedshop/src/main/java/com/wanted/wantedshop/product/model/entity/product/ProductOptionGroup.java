package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.ProductOptionGroupDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_option_groups")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductOptionGroup {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductOption> productOptions = new ArrayList<>();

    @ColumnDefault("0")
    private Integer displayOrder;

    public static ProductOptionGroup of(Long productId, ProductOptionGroupDto dto, List<ProductOption> productOptionsList) {
        return ProductOptionGroup.builder()
                .product(Product.ofId(productId))
                .name(dto.getName())
                .displayOrder(dto.getDisplayOrder())
                .productOptions(productOptionsList)
                .build();
    }
}
