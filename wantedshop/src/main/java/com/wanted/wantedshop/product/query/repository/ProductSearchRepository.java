package com.wanted.wantedshop.product.query.repository;


import com.wanted.wantedshop.product.query.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearchDocument, Long> {
}