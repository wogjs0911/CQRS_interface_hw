package com.wanted.wantedshop.product.model.entity.product;

import com.wanted.wantedshop.common.exception.BaseResDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Seller extends BaseResDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(nullable = false)
    private String name;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 255)
    private String logoUrl;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Size(max = 100)
    private String contactEmail;

    @Size(max = 20)
    private String contactPhone;
}
