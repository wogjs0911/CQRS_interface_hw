package com.wanted.wantedshop.product.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductDetail is a Querydsl query type for ProductDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetail extends EntityPathBase<ProductDetail> {

    private static final long serialVersionUID = 1302857777L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductDetail productDetail = new QProductDetail("productDetail");

    public final MapPath<String, Object, SimplePath<Object>> additionalInfo = this.<String, Object, SimplePath<Object>>createMap("additionalInfo", String.class, Object.class, SimplePath.class);

    public final StringPath careInstructions = createString("careInstructions");

    public final StringPath countryOfOrigin = createString("countryOfOrigin");

    public final MapPath<String, Object, SimplePath<Object>> dimensions = this.<String, Object, SimplePath<Object>>createMap("dimensions", String.class, Object.class, SimplePath.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath materials = createString("materials");

    public final QProduct product;

    public final StringPath warrantyInfo = createString("warrantyInfo");

    public final NumberPath<java.math.BigDecimal> weight = createNumber("weight", java.math.BigDecimal.class);

    public QProductDetail(String variable) {
        this(ProductDetail.class, forVariable(variable), INITS);
    }

    public QProductDetail(Path<? extends ProductDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductDetail(PathMetadata metadata, PathInits inits) {
        this(ProductDetail.class, metadata, inits);
    }

    public QProductDetail(Class<? extends ProductDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

