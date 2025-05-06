package com.wanted.wantedshop.common.domain;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ResponseHandler<T>(boolean success, T data, String message) {
    public static <T> ResponseHandler<T> of(boolean success, T data, String message) {
        return new ResponseHandler<>(success, data, message);
    }
}