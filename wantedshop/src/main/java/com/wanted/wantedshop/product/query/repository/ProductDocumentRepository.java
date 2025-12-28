package com.wanted.wantedshop.product.query.repository;

import com.wanted.wantedshop.product.query.entity.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDocumentRepository extends MongoRepository<ProductDocument, Long> {
}
