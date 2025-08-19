package com.wanted.wantedshop.product.application;

import com.wanted.wantedshop.common.exception.ResultCode;
import com.wanted.wantedshop.common.exception.ServiceException;
import com.wanted.wantedshop.product.infrastructure.repository.*;
import com.wanted.wantedshop.product.model.dto.request.*;
import com.wanted.wantedshop.product.model.dto.response.ProductSearchResponse;
import com.wanted.wantedshop.product.model.entity.Tag.Tag;
import com.wanted.wantedshop.product.model.entity.category.Category;
import com.wanted.wantedshop.product.model.entity.product.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final ProductDetailRepository detailRepository;
    private final ProductPriceRepository priceRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductOptionRepository optionRepository;
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

        // 1) 기본 Product 엔티티 생성
        Product product = Product.from(saveRequest);

        // 2) 연관 엔티티 설정 v2
        if(saveRequest.getSellerId() != null){
           Seller seller = sellerRepository.findById(saveRequest.getSellerId())
                     .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
           product.setSeller(seller);
        }

        if(saveRequest.getBrandId() != null){
            Brand brand = brandRepository.findById(saveRequest.getBrandId())
                    .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
            product.setBrand(brand);
        }

        // 3) 유효성 검사
        Optional.of(repository.findBySlug(saveRequest.getSlug()))
                .filter(List::isEmpty)
                .orElseThrow(() -> new ServiceException(ResultCode.DUPLICATE_INFO));

        Optional.of(repository.findByName(saveRequest.getName()))
                .filter(List::isEmpty)
                .orElseThrow(() -> new ServiceException(ResultCode.DUPLICATE_INFO));

        // 4) Product save : save 반복 사용 x
        product = repository.save(Product.from(saveRequest));

        // 5) 연관 관계 설정 및 저장
        // a. ProductDetail set v2
        if(saveRequest.getDetail() != null){
            ProductDetail detail = ProductDetail.of(product, saveRequest.getDetail());
            product.setDetail(detail);
        }

        // b. ProductPrice set v2
        if(saveRequest.getPrice() != null){
            ProductPrice price = ProductPrice.of(product, saveRequest.getPrice());
            product.setPrice(price);
        }

        // 6) ProductCategory set v2
        if (saveRequest.getCategories() != null && !saveRequest.getCategories().isEmpty()) {
            List<Long> categoryIdList = saveRequest.getCategories().stream()
                .map(ProductCategoryDto::getCategoryId)
                .toList();

            List<Category> categoryList = categoryRepository.findAllById(categoryIdList);

            List<ProductCategory> productCategories = new ArrayList<>();
            for (ProductCategoryDto dto : saveRequest.getCategories()) {
                // Category 엔티티 찾아오기
                Category category = categoryList.stream()
                        .filter(c -> c.getId().equals(dto.getCategoryId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));

                productCategories.add(ProductCategory.of(product, category, dto));
            }

            product.getCategories().addAll(productCategories);
        }

        // 7) ProductOptionGroup, ProductOption set v2
        if(saveRequest.getOptionGroups() != null && !saveRequest.getOptionGroups().isEmpty()){
            for(ProductOptionGroupDto optionGroupDto : saveRequest.getOptionGroups()){
                ProductOptionGroup group = ProductOptionGroup.of(product, optionGroupDto);
                product.getOptionGroups().add(group);

                if (optionGroupDto.getOptions() != null) {
                    for(ProductOptionDto optionDto : optionGroupDto.getOptions()){
                        ProductOption option = ProductOption.of(group, optionDto);
                        group.getProductOptions().add(option);
                    }
                }
            }
        }

        // 8) ProductImage set v2
        if(saveRequest.getImages() != null && !saveRequest.getImages().isEmpty()){
            for(ProductImageDto productImageDto : saveRequest.getImages()){
                ProductOption option = null;
                if (productImageDto.getOptionId() != null) {
                    option = optionRepository.findById(productImageDto.getOptionId())
                            .orElse(null);
                }
                ProductImage productImage = ProductImage.of(productImageDto, product, option);
                product.getImages().add(productImage);
            }
        }

        // 9) ProductTag set v2
        if(saveRequest.getTags() != null && !saveRequest.getTags().isEmpty()){
            List<Tag> tagsIdList = tagRepository.findAllById(saveRequest.getTags());
            List<ProductTag> productTagList = new ArrayList<>();
            for (Tag tag : tagsIdList) {
                productTagList.add(ProductTag.of(product, tag));
            }
            product.getTags().addAll(productTagList);
        }

        product = repository.save(product);
        return product.getId();
    }

    @Transactional
    public Long updateProduct(Long id, ProductSaveRequest saveRequest) {
        // 1) product update
        Product product = repository.findById(id)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));

        product.updateProductEntity(saveRequest);

        // 2) 연관 엔티티 업데이트
        if(saveRequest.getSellerId() != null){
           Seller seller = sellerRepository.findById(saveRequest.getSellerId())
                     .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
           product.setSeller(seller);
        }

        if(saveRequest.getBrandId() != null){
            Brand brand = brandRepository.findById(saveRequest.getBrandId())
                    .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
            product.setBrand(brand);
        }

        // 3) productDetail update
        ProductDetail detail = detailRepository.findByProductId(id)
            .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
        detail.update(saveRequest.getDetail());

        // 4) productPrice update
        ProductPrice price = priceRepository.findByProductId(id)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
        price.update(saveRequest.getPrice());

        // 5) ProductCategory set v2
        if (saveRequest.getCategories() != null) {
            product.getCategories().clear();
            List<Long> categoryIdList = saveRequest.getCategories().stream()
                .map(ProductCategoryDto::getCategoryId)
                .toList();

            List<Category> categoryList = categoryRepository.findAllById(categoryIdList);

            List<ProductCategory> productCategories = new ArrayList<>();
            for (ProductCategoryDto dto : saveRequest.getCategories()) {
                // Category 엔티티 찾아오기
                Category category = categoryList.stream()
                        .filter(c -> c.getId().equals(dto.getCategoryId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));

                productCategories.add(ProductCategory.of(product, category, dto));
            }
            product.getCategories().addAll(productCategories);
        }
        return product.getId();
    }

    @Transactional
    public Long deleteProduct(Long id, ProductSaveRequest saveRequest) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
        repository.delete(product);
        return product.getId();
    }
}
