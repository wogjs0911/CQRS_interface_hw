package com.wanted.wantedshop.product.infrastructure.repository.projection;

public interface OptionGroupWithProductProjection {
    Long getId();
    String getName();
    Integer getDisplayOrder();
    Long getProductId();
}
