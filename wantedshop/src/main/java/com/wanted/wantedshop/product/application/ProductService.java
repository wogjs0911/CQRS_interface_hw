package com.wanted.wantedshop.product.application;

import com.wanted.wantedshop.common.exception.ResultCode;
import com.wanted.wantedshop.common.exception.ServiceException;
import com.wanted.wantedshop.product.infrastructure.repository.*;
import com.wanted.wantedshop.product.model.dto.request.ProductOptionGroupDto;
import com.wanted.wantedshop.product.model.dto.request.ProductSaveRequest;
import com.wanted.wantedshop.product.model.dto.request.ProductSearchRequest;
import com.wanted.wantedshop.product.model.dto.response.ProductSearchResponse;
import com.wanted.wantedshop.product.model.entity.product.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductDetailRepository detailRepository;
    private final ProductPriceRepository priceRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductImageRepository imageRepository;
    private final ProductTagRepository tagRepository;

    public Page<ProductSearchResponse> getProductsByConditions(ProductSearchRequest searchRequest) {
        return repository.findProductsByConditions(searchRequest);
    }

    public Product getListById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
    }

    @Transactional
    public Long saveProduct(ProductSaveRequest saveRequest) {
        // 1) 유효성 체크
        Optional.of(repository.findBySellerId(saveRequest.getSellerId()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));

        Optional.of(repository.findByBrandId(saveRequest.getBrandId()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));

        Optional.of(repository.findBySlug(saveRequest.getSlug()))
                .filter(List::isEmpty)
                .orElseThrow(() -> new ServiceException(ResultCode.DUPLICATE_INFO));

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

        // 6) ProductOptionGroup save
        List<ProductOptionGroup> productOptionGroupList = saveRequest.getOptionGroups()
                .stream()
                .map(dto -> ProductOptionGroup.of(product.getId(), dto))
                .collect(Collectors.toList());
        optionGroupRepository.saveAll(productOptionGroupList);

        // 7) ProductOption save
        List<ProductOption> productOptionsList = IntStream.range(0, saveRequest.getOptionGroups().size())
                .boxed()
                .flatMap(i -> {
                    ProductOptionGroupDto groupDto = saveRequest.getOptionGroups().get(i);
                    ProductOptionGroup groupEntity = productOptionGroupList.get(i);
                    return groupDto.getOptions().stream()
                            .map(dto -> ProductOption.of(groupEntity.getId(), dto));
                })
                .collect(Collectors.toList());
        optionRepository.saveAll(productOptionsList);

        // 8) ProductImage save
        List<ProductImage> productImageList = saveRequest.getImages()
                .stream()
                .map(dto -> ProductImage.of(product.getId(), dto))
                .collect(Collectors.toList());
        imageRepository.saveAll(productImageList);

        // 9) ProductTag save
        List<ProductTag> productTagDtoList = saveRequest.getTags()
                .stream()
                .map(tags -> ProductTag.of(product.getId(), tags))
                .collect(Collectors.toList());
        tagRepository.saveAll(productTagDtoList);

        return product.getId();
    }

    @Transactional
    public Long updateProduct(Long id, ProductSaveRequest saveRequest) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
        product.update(saveRequest);
        return product.getId();
    }
}
