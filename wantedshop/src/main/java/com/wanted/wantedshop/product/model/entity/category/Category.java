package com.wanted.wantedshop.product.model.entity.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
}
