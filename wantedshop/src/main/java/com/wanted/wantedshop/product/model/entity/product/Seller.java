package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.common.BaseEntity;
import com.wanted.wantedshop.common.exception.BaseResDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Seller extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String name;
    private String description;
    private String logoUrl;
    private BigDecimal rating;
    private String contactEmail;
    private String contactPhone;

    public static Seller ofId(Long id) {
        return id == null ? null : Seller.builder().id(id).build();
    }
}
