package com.wanted.wantedshop.product.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.wantedshop.product.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, ProductRepositoryCustom {
}
