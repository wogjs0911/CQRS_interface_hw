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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductDetailRepository detailRepository;
    private final ProductPriceRepository priceRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductImageRepository imageRepository;

    public Page<ProductSearchResponse> getProductsByConditions(ProductSearchRequest searchRequest) {
        return repository.findProductsByConditions(searchRequest);
    }

    public Product getListById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
    }

    @Transactional
    public Long saveProduct(ProductSaveRequest saveRequest) {
        // TODO : PRODUCT createdAt, updatedAt 데이터 UPDATE , OPTIONS : option_group_id 데이터 INSERT , TAGS 데이터 INSERT
        // 1) 유효성 체크
        Optional.of(repository.findBySellerId(saveRequest.getSellerId()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));

        Optional.of(repository.findByBrandId(saveRequest.getBrandId()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));

        // 2) Product save
        Product product = repository.save(Product.from(saveRequest));

        // 3) ProductDetail save
        detailRepository.save(ProductDetail.of(product.getId(), saveRequest.getDetail()));

        // 4) ProductPrice save
        priceRepository.save(ProductPrice.of(product.getId(), saveRequest.getPrice()));

        // 5) ProductCategory save
        List<ProductCategory> productCategoryList = saveRequest.getCategories()
            .stream()
            .map(dto -> ProductCategory.of(product.getId(), dto))
            .collect(Collectors.toList());
        categoryRepository.saveAll(productCategoryList);

        // 6) ProductOption getList
        List<ProductOption> productOptionsList = saveRequest.getOptionGroups()
            .stream()
            .flatMap(groupDto -> groupDto.getOptions().stream())
            .map(ProductOption::from)
            .collect(Collectors.toList());

        // 7) ProductOptionGroup save
        List<ProductOptionGroup> productOptionGroupList = saveRequest.getOptionGroups()
            .stream()
            .map(dto -> ProductOptionGroup.of(product.getId(), dto, productOptionsList))
            .collect(Collectors.toList());
        optionGroupRepository.saveAll(productOptionGroupList);

        // 8) ProductImage save
        List<ProductImage> productImageList = saveRequest.getImages()
                .stream()
                .map(dto -> ProductImage.of(product.getId(), dto))
                .collect(Collectors.toList());
        imageRepository.saveAll(productImageList);

        return product.getId();
    }
}
