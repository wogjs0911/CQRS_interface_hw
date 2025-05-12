package com.wanted.wantedshop.product.model.dto.request;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class AdditionalInfo {
    private Boolean assemblyRequired;
    private String assemblyTime;
}