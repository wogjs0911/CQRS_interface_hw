package com.wanted.wantedshop.product.model.dto.request;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class DimensionsInfo {
    private Integer width;
    private Integer height;
    private Integer depth;
}