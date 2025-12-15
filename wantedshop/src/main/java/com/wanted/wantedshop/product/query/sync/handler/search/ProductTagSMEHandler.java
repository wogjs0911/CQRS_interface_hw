package com.wanted.wantedshop.product.query.sync.handler.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.wantedshop.product.query.search.ProductSearchDocument;
import com.wanted.wantedshop.product.query.search.ProductSearchRepository;
import com.wanted.wantedshop.product.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class ProductTagSMEHandler extends ProductSearchModelEventHandler{

    private final ElasticsearchOperations elasticsearchOperations;
    private final ProductSearchRepository productSearchRepository;

    public ProductTagSMEHandler(ObjectMapper objectMapper
            , ElasticsearchOperations elasticsearchOperations
            , ProductSearchRepository productSearchRepository) {
        super(objectMapper);
        this.elasticsearchOperations = elasticsearchOperations;
        this.productSearchRepository = productSearchRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "product_tags";
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

        if (data == null || !data.containsKey("product_id") || !data.containsKey("tag_id")) {
            return;
        }

        productId = getLongValue(data, "product_id");
        Long tagId = getLongValue(data, "tag_id");

        Optional<ProductSearchDocument> productOptionalDocument = productSearchRepository.findById(productId);
        if (productOptionalDocument.isEmpty()) {
            log.warn("Product document not found for tag updates with productId: {}", productId);
            return;
        }

        ProductSearchDocument productSearchDocument = productOptionalDocument.get();

        List<Long> tagIds = productSearchDocument.getTagIds();
        if(tagIds == null || tagIds.isEmpty()){
            tagIds = new ArrayList<>();
        }

        boolean updated = false;
        if(event.isDelete()){
            updated = tagIds.remove(tagId);
        } else {
            tagIds.add(tagId);
            updated = true;
        }

        if(updated){
            Map<String, Object> updates = new HashMap<>();
            updates.put("tagIds", tagIds);
            updatePartialDocument(productId, updates);
        }
    }

    private void updatePartialDocument(Long productId, Map<String, Object> updates) {
        if(productId == null || updates == null || updates.isEmpty()) {
            return;
        }

        try {
            // Document 객체 생성(Spring Data Elasticsearch의 부분 업데이트에 사용할 문서)
            Document document = Document.create();

            // 각 필드를 Document에 추가
            updates.forEach(document::put);

            // Spring Data Elasticsearch가 제공하는 업데이트 요청 객체
            UpdateQuery updateQuery = UpdateQuery.builder(productId.toString())
                    .withDocument(document)
                    .withDocAsUpsert(true)
                    .build();

            // 실제 업데이트 수행
            elasticsearchOperations.update(updateQuery, IndexCoordinates.of("products"));
            log.debug("Partially updated document: {}", productId);
        } catch (Exception e) {
            log.error("Error updating document {}: {}", productId, e.getMessage());
        }
    }
}
