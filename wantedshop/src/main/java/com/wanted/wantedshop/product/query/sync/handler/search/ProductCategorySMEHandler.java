package com.wanted.wantedshop.product.query.sync.handler.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.wantedshop.product.query.search.ProductSearchDocument;
import com.wanted.wantedshop.product.query.search.ProductSearchRepository;
import com.wanted.wantedshop.product.query.sync.CdcEvent;
import com.wanted.wantedshop.product.query.sync.handler.AbstractCdcEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class ProductCategorySMEHandler extends AbstractCdcEventHandler {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ProductSearchRepository productSearchRepository;

    public ProductCategorySMEHandler(ObjectMapper objectMapper
            , ElasticsearchOperations elasticsearchOperations
            , ProductSearchRepository productSearchRepository) {
        super(objectMapper);
        this.elasticsearchOperations = elasticsearchOperations;
        this.productSearchRepository = productSearchRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "product_categories";
    }

    @Override
    public void handle(CdcEvent event) {
        Map<String, Object> data;
        Long productId;

        // data는 CDC 이벤트에서 넘어온 행 데이터 맵
        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null || !data.containsKey("product_id") || !data.containsKey("category_id")) {
            return;
        }

        productId = getLongValue(data, "product_id");
        Long categoryId = getLongValue(data, "category_id");

        // 카테고리 목록을 조회해야 함 - 기존 문서 필요
        Optional<ProductSearchDocument> optionalDocument = productSearchRepository.findById(productId);
        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for category update: {}", productId);
            return;
        }

        ProductSearchDocument document = optionalDocument.get();

        // 기존 카테고리 목록 가져오기
        List<Long> categoryIds = document.getCategoryIds();
        if (categoryIds == null) {
            categoryIds = new ArrayList<>();
        }

        // 카테고리 매핑 추가 또는 제거
        boolean updated = false;
        if (event.isDelete()) {
            updated = categoryIds.remove(categoryId);
        } else if (!categoryIds.contains(categoryId)) {
            categoryIds.add(categoryId);
            updated = true;
        }

        // 변경된 경우에만 업데이트
        if (updated) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("categoryIds", categoryIds);
            updatePartialDocument(productId, updates);
        }
    }

    // 부분 업데이트를 위한 메서드
    private void updatePartialDocument(Long productId, Map<String, Object> updates) {
        if (productId == null || updates == null || updates.isEmpty()) {
            return;
        }

        try {
            // Document 객체 생성
            Document document = Document.create();

            // 각 필드를 Document에 추가
            updates.forEach(document::put);

            UpdateQuery updateQuery = UpdateQuery.builder(productId.toString())
                    .withDocument(document)
                    .withDocAsUpsert(true) // 문서가 없으면 생성
                    .build();

            elasticsearchOperations.update(updateQuery, IndexCoordinates.of("products"));
            log.debug("Partially updated document: {}", productId);
        } catch (Exception e) {
            log.error("Error updating document {}: {}", productId, e.getMessage());
        }
    }
}
