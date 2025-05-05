package com.wanted.wantedshop.product.model.entity.review;

import com.wanted.wantedshop.common.exception.BaseResDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseResDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(nullable = false, length = 100)
    private String email;

    @Size(max = 255)
    private String avatarUrl;
}