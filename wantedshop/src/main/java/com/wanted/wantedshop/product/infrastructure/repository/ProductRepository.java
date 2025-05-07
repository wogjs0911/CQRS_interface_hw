package com.wanted.wantedshop.product.infrastructure.repository;

import com.wanted.wantedshop.product.infrastructure.repository.custom.ProductRepositoryCustom;
import com.wanted.wantedshop.product.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Optional<Product> findByName(String name);
    Optional<Product> findByBrandId(Long brandId);
    Optional<Product> findBySellerId(Long sellerId);
}
