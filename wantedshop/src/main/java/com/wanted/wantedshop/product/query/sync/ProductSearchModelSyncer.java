package com.wanted.wantedshop.product.query.sync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.wantedshop.product.query.sync.handler.CdcEventHandler;
import com.wanted.wantedshop.product.query.sync.handler.search.ProductSearchModelEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchModelSyncer {

    private final ObjectMapper objectMapper;
    private final List<ProductSearchModelEventHandler> eventHandlers;

    @KafkaListener(topics = {"product-events"}, groupId = "product-search-group")
    public void consumeProductEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        try {
            // 디버깅을 위한 원본 메시지 로깅
            log.debug("Received CDC event - Key: {}, Value: {}", messageKey, messageValue);

            // 값 데이터 파싱
            CdcEvent event = objectMapper.readValue(messageValue, CdcEvent.class);

            // 키 데이터 파싱 및 설정
            if (messageKey != null && !messageKey.isEmpty()) {
                Map<String, Object> keyData = objectMapper.readValue(messageKey,
                        new TypeReference<Map<String, Object>>() {});
                event.setKey(keyData);
            }

            String table = event.getTable();
            if (table == null) {
                log.warn("Table name missing in event");
                return;
            }

            // 이벤트 타입에 따라 처리: Strategy, CoR
            boolean handled = false;
            for(CdcEventHandler handler: eventHandlers) {
                if(handler.canHandle(event)) {
                    handler.handle(event);
                    handled = true;
                    break;
                }
            }

            if(!handled) {
                log.warn("No handler found for table: {}", table);
            }
        } catch (JsonProcessingException e) {
            log.error("Error parsing CDC event: {}", messageValue, e);
        } catch (Exception e) {
            log.error("Error processing CDC event: {}", messageValue, e);
        }
    }
}