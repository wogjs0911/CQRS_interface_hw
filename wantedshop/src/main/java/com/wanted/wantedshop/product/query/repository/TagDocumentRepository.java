package com.wanted.wantedshop.product.query.repository;

import com.wanted.wantedshop.product.query.entity.TagDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDocumentRepository extends MongoRepository<TagDocument, Long> {
}
