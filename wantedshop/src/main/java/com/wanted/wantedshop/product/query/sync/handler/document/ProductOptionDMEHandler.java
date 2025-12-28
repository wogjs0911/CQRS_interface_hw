package com.wanted.wantedshop.product.query.sync.handler.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.wantedshop.product.infrastructure.repository.ProductOptionGroupRepository;
import com.wanted.wantedshop.product.infrastructure.repository.projection.OptionGroupWithProductProjection;
import com.wanted.wantedshop.product.query.entity.ProductDocument;
import com.wanted.wantedshop.product.query.repository.ProductDocumentRepository;
import com.wanted.wantedshop.product.query.sync.CdcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ProductOptionDMEHandler extends ProductDocumentModelEventHandler {
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductDocumentRepository productDocumentRepository;

    public ProductOptionDMEHandler(ObjectMapper objectMapper,
                                   ProductOptionGroupRepository productOptionGroupRepository,
                                   ProductDocumentRepository  productDocumentRepository) {
        super(objectMapper);
        this.productOptionGroupRepository = productOptionGroupRepository;
        this.productDocumentRepository = productDocumentRepository;
    }

    @Override
    protected String getSupportedTable() {
        return "product_options";
    }

    @Override
    public void handle(CdcEvent event) {
        Map<String, Object> data;
        Long optionGroupId;
        Long optionId;

        if (event.isDelete()) {
            data = event.getBeforeData();
        } else {
            data = event.getAfterData();
        }

        if (data == null || !data.containsKey("option_group_id")) {
            return;
        }

        optionGroupId = getLongValue(data, "option_group_id");
        optionId = getLongValue(data, "id");

        // Projection에서 optionGroupId 조회
        Optional<OptionGroupWithProductProjection> optionGroupProjection =
                productOptionGroupRepository.findOptionGroupWithProductProjection(optionGroupId);

        if (optionGroupProjection.isEmpty()) {
            log.warn("optional group projection not found : {}", optionGroupId);
            return;
        }

        Long productId = optionGroupProjection.get().getProductId();


        // MongoDB에서 상품 문서 조회
        Optional<ProductDocument> optionalDocument = productDocumentRepository.findById(productId);
        if (optionalDocument.isEmpty()) {
            log.warn("Product document not found for option update: {}", productId);
            return;
        }

        ProductDocument document = optionalDocument.get();
        Optional<ProductDocument.OptionGroup> optionGroupOpt = document.getOptionGroups().stream()
                .filter(group -> group.getId().equals(optionGroupId))
                .findFirst();

        if(optionGroupOpt.isEmpty()){
            log.warn("optional group {} not found in product : {}", optionGroupId, productId);
            return;
        }

        ProductDocument.OptionGroup optionGroup = optionGroupOpt.get();

        // 삭제 이벤트 처리
        if (event.isDelete()) {
            optionGroup.getOptions().removeIf(group -> group.getId().equals(optionId));
            productDocumentRepository.save(document);
            log.warn("Removed option {} from option group: {}", optionId, optionGroupId);
            return;
        }

        // 기존 옵션 그룹 찾거나 새로 생성
        Optional<ProductDocument.Option> existingOptionGroup = optionGroup.getOptions().stream()
                .filter(group -> group.getId().equals(optionId))
                .findFirst();

        ProductDocument.Option option;

        if(!existingOptionGroup.isEmpty()){
            option = existingOptionGroup.get();
        } else {
            option = ProductDocument.Option.builder()
                    .id(optionId)
                    .images(document.getImages())
                    .build();

            optionGroup.getOptions().add(option);
        }

        // 옵션 그룹 정보 업데이트
        if (data.containsKey("name")) {
            option.setName(getStringValue(data, "name"));
        }

        if (data.containsKey("additional_price")) {
            option.setAdditionalPrice(getBigDecimalValue(data, "additional_price"));
        }
        if (data.containsKey("sku")) {
            option.setSku(getStringValue(data, "sku"));
        }
        if (data.containsKey("stock")) {
            option.setStock(getIntegerValue(data, "stock"));
        }

        if (data.containsKey("display_order")) {
            option.setDisplayOrder(getIntegerValue(data, "display_order"));
        }

        productDocumentRepository.save(document);
        log.info("Updated option mapping for product ID: {}, option Id: {}", productId, optionId);
    }
}
