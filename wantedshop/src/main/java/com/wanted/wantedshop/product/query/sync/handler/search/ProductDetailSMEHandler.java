package com.wanted.wantedshop.product.query.sync.handler.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.wantedshop.product.query.search.ProductSearchRepository;
import com.wanted.wantedshop.product.query.sync.CdcEvent;
import com.wanted.wantedshop.product.query.sync.handler.AbstractCdcEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ProductDetailSMEHandler extends AbstractCdcEventHandler {

    private final ElasticsearchOperations elasticsearchOperations;

    public ProductDetailSMEHandler(ObjectMapper objectMapper
            , ElasticsearchOperations elasticsearchOperations) {
        super(objectMapper);
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    protected String getSupportedTable() {
        return "product_details";
    }

    @Override
    public void handle(CdcEvent event) {
        Map<String, Object> data;
        Long productId;

        // data는 CDC 이벤트에서 넘어온 행 데이터 맵
        if(event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if(data == null || !data.containsKey("product_id")) {
            return;
        }

        productId = getLongValue(data, "product_id");

        // afterData/beforeData에서 해당 키가 존재하면 ES 문서의 materials 필드를 갱신/삭제(null)
        // 삭제 이벤트 처리
        if(event.isDelete()) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("materials", null);
            updatePartialDocument(productId, updates);
            return;
        }

        // 부분 업데이트로 처리
        if(data.containsKey("materials")) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("materials", getStringValue(data, "materials"));
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
