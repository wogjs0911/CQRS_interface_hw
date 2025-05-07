package com.wanted.wantedshop.product.application;

import com.wanted.wantedshop.common.exception.ResultCode;
import com.wanted.wantedshop.common.exception.ServiceException;
import com.wanted.wantedshop.product.infrastructure.repository.*;
import com.wanted.wantedshop.product.model.dto.request.ProductSaveRequest;
import com.wanted.wantedshop.product.model.dto.request.ProductSearchRequest;
import com.wanted.wantedshop.product.model.dto.response.ProductSearchResponse;
import com.wanted.wantedshop.product.model.entity.product.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductDetailRepository detailRepository;
    private final ProductPriceRepository priceRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductImageRepository imageRepository;

    public Product getListById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
    }

    public Product getByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
    }

    public Page<ProductSearchResponse> getProductsByConditions(ProductSearchRequest searchRequest) {
        return repository.findProductsByConditions(searchRequest);
    }

    @Transactional
    public Long saveProduct(ProductSaveRequest saveRequest) {
    // 1. 상품 생성 + Detail
    // 2. 가격 저장
    // 3. 카테고리 매핑
    // 4. 옵션 그룹 및 옵션
    // 5. 이미지 저장
//        return repository.save(saveRequest).getId();

        // 1) 유효성 체크
        repository.findBySellerId(saveRequest.getSellerId())
            .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));

        repository.findByBrandId(saveRequest.getBrandId())
            .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));

        // 2) Product save
        Product product = repository.save(Product.from(saveRequest));

        // 3) ProductDetail save

        // 4) ProductPrice save

        // 5) ProductCategory save

        // 6) ProductOption save

        // 7) ProductOptionGroup save

        // 8) ProductImage save


        return product.getId();
    }

    public List<Product> getAllProductList() {
        List<Product> productList = repository.findAll();
        if (productList.isEmpty()) {
            throw new ServiceException(ResultCode.VALID_NOT_NULL);
        }
        return productList;
    }
}
