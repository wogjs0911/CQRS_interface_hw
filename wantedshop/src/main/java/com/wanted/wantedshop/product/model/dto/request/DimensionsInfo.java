package com.wanted.wantedshop.product.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DimensionsInfo {
    private Integer width;
    private Integer height;
    private Integer depth;
}