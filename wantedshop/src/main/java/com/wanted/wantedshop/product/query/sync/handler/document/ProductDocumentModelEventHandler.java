package com.wanted.wantedshop.product.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.wantedshop.product.query.sync.handler.AbstractCdcEventHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public abstract class ProductDocumentModelEventHandler extends AbstractCdcEventHandler {
    public ProductDocumentModelEventHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    protected LocalDateTime parseTimestampToLocalDateTime(Object timestamp) {
        if (timestamp == null) {
            return null;
        }

        if (timestamp instanceof String) {
            try {
                return LocalDateTime.parse((String) timestamp);
            } catch (Exception e) {
                log.warn("Failed to parse timestamp string: {}", timestamp);
                return null;
            }
        } else if (timestamp instanceof Number) {
            try {
                // 마이크로초 단위를 밀리초로 변환 (1/1000)
                long microseconds = ((Number) timestamp).longValue();
                long milliseconds = microseconds / 1000;

                Instant instant = Instant.ofEpochMilli(milliseconds);
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            } catch (Exception e) {
                log.warn("Failed to parse timestamp number: {}", timestamp);
                return null;
            }
        }

        return null;
    }
}
