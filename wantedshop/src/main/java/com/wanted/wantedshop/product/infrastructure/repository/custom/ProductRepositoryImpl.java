package com.wanted.wantedshop.product.infrastructure.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.wantedshop.product.model.dto.request.ProductSearchRequest;
import com.wanted.wantedshop.product.model.dto.response.ProductSearchResponse;
import com.wanted.wantedshop.product.model.entity.product.*;
import com.wanted.wantedshop.product.model.entity.review.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.wanted.wantedshop.common.SortUtil.parseMultiSortString;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductSearchResponse> findProductsByConditions(ProductSearchRequest searchRequest) {
        QProduct product = QProduct.product;
        QProductPrice productPrice = QProductPrice.productPrice;
        QBrand brand = QBrand.brand;
        QSeller seller = QSeller.seller;
        QProductImage productImage = QProductImage.productImage;
        QReview review = QReview.review;

        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(searchRequest.getSort());

        // 둘 이상이면 타입을 명확하게 지정할 수 없으므로 튜플이나 DTO로 조회
        List<ProductSearchResponse> content = queryFactory
                .select(Projections.constructor(ProductSearchResponse.class,
                        product.id,
                        product.name,
                        product.slug,
                        product.shortDescription,
                        productPrice.basePrice,
                        productPrice.salePrice,
                        (select(productImage.url)
                                .from(productImage)
                                .where(productImage.product.eq(product)
                                        .and(productImage.isPrimary.isTrue()))
                                .orderBy(productImage.displayOrder.asc())
                                .limit(1)), // 대표 이미지 1개만
                        brand.name.as("brandName"), // brand.name을 "brandName"으로 매핑
                        review.rating.avg() // 평균 평점은 Double로 반환
                ))
                .from(product)
                .leftJoin(seller).on(seller.eq(product.seller))
                .leftJoin(brand).on(brand.eq(product.brand))
                .leftJoin(productPrice).on(productPrice.product.eq(product))
                .leftJoin(review).on(review.product.eq(product))
                .where(buildSearchCondition(searchRequest))
                .groupBy(product.id, productPrice.id, brand.name)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset((long) searchRequest.getPage() * searchRequest.getPerPage())  // 페이지네이션 처리
                .limit(searchRequest.getPerPage())  // 페이지네이션 처리
                .fetch();

        // totalCount 조회
        Long totalCount = queryFactory
                .select(product.count())
                .from(product)
                .leftJoin(seller).on(seller.eq(product.seller))
                .leftJoin(brand).on(brand.eq(product.brand))
                .leftJoin(productPrice).on(productPrice.product.eq(product))
                .leftJoin(review).on(review.product.eq(product))
                .where(buildSearchCondition(searchRequest))
                .fetchOne();

        return new PageImpl<>(
                content,
                PageRequest.of(searchRequest.getPage(), searchRequest.getPerPage()),
                totalCount != null? totalCount : 0L
        );
    }

    private BooleanBuilder buildSearchCondition(ProductSearchRequest searchRequest){
        QProduct product = QProduct.product;
        QProductPrice productPrice = QProductPrice.productPrice;
        QBrand brand = QBrand.brand;
        QSeller seller = QSeller.seller;
        QProductCategory productCategory = QProductCategory.productCategory;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(searchRequest.getMaxPrice() != null){
            booleanBuilder.and(productPrice.basePrice.loe(searchRequest.getMaxPrice()));
        }

        if(searchRequest.getMinPrice() != null){
            booleanBuilder.and(productPrice.basePrice.goe(searchRequest.getMinPrice()));
        }

        return booleanBuilder;
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(String sortParam) {
        QProduct product = QProduct.product;
        QProductPrice productPrice = QProductPrice.productPrice;
        QReview review = QReview.review;

        Sort sort = parseMultiSortString(sortParam);
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        if(sort != null){
            for(Sort.Order order: sort){
                switch(order.getProperty()){
                    case "created_at" ->
                        orderSpecifiers.add(order.isAscending() ? product.createdAt.asc() : product.createdAt.desc());
                    case "sale_price" ->
                            orderSpecifiers.add(order.isAscending() ? productPrice.salePrice.asc() : productPrice.salePrice.desc());
                    case "rating" ->
                            orderSpecifiers.add(order.isAscending() ? review.rating.avg().asc() : review.rating.avg().desc());
                    default -> {
                    }
                }
            }
        }
        return orderSpecifiers;
    }

}
