package com.wanted.wantedshop.product.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalInfo {
    private Boolean assemblyRequired;
    private String assemblyTime;
}