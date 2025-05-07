package com.wanted.wantedshop.product.infrastructure.repository;

import com.wanted.wantedshop.product.model.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
