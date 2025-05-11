package com.wanted.wantedshop.product.infrastructure.repository;

import com.wanted.wantedshop.product.model.entity.product.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
}
