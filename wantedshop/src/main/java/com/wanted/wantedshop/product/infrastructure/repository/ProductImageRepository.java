package com.wanted.wantedshop.product.infrastructure.repository;

import com.wanted.wantedshop.product.model.entity.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
