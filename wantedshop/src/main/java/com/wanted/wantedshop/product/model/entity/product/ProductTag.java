package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.product.model.dto.request.ProductTagDto;
import com.wanted.wantedshop.product.model.entity.Tag.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_tags")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductTag {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public static ProductTag of(Long productId, Long tag){
        return ProductTag.builder()
                .product(Product.ofId(productId))
                .tag(Tag.ofId(tag))
                .build();

    }
}
