package com.wanted.wantedshop.common;

import org.springframework.data.domain.Sort;

import java.util.Arrays;

public class SortUtil {
    public static Sort parseSortString(String sortParam) {
        if (sortParam == null || sortParam.isBlank()) {
            return Sort.unsorted();
        }

        String[] parts = sortParam.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("정렬 형식은 '필드명:방향(asc/desc)'이어야 합니다.");
        }

        String property = parts[0].trim();
        Sort.Direction direction = Sort.Direction.fromString(parts[1].trim());

        return Sort.by(new Sort.Order(direction, property));
    }

    // optional: 다중 정렬 지원
    public static Sort parseMultiSortString(String sortParams) {
        if (sortParams == null || sortParams.isBlank()) {
            return Sort.unsorted();
        }

        return Sort.by(
                Arrays.stream(sortParams.split(","))
                        .map(SortUtil::parseSortString)
                        .flatMap(s -> s.stream()) // Sort → Stream<Order>
                        .toList()
        );
    }
}
