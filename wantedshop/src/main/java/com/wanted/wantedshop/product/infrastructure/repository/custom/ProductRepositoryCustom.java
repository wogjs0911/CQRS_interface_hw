package com.wanted.wantedshop.product.infrastructure.repository.custom;

import com.wanted.wantedshop.product.model.dto.request.ProductSearchRequest;
import com.wanted.wantedshop.product.model.dto.response.ProductSearchResponse;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {
    Page<ProductSearchResponse> findProductsByConditions(ProductSearchRequest searchRequest);
}
