package com.wanted.wantedshop.product.query.repository;

import com.wanted.wantedshop.product.query.entity.BrandDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandDocumentRepository extends MongoRepository<BrandDocument, Long> {
}
