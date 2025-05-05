package com.wanted.wantedshop.product.model.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "brands")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

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

    @Size(max = 255)
    private String logoUrl;

    @Size(max = 255)
    private String website;
}
