package com.wanted.wantedshop.product.query.repository;

import com.wanted.wantedshop.product.query.entity.SellerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerDocumentRepository extends MongoRepository<SellerDocument, Long> {

}
