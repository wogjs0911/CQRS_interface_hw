package com.wanted.wantedshop.product.infrastructure.repository;

import com.wanted.wantedshop.product.model.entity.product.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
}
