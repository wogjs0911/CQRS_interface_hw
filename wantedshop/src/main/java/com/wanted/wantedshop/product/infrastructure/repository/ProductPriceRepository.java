package com.wanted.wantedshop.product.infrastructure.repository;

import com.wanted.wantedshop.product.model.entity.product.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
