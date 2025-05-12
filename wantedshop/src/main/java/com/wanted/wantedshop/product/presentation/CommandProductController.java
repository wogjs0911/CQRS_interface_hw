package com.wanted.wantedshop.product.presentation;


import com.wanted.wantedshop.common.domain.ResponseHandler;
import com.wanted.wantedshop.common.exception.ResultCode;
import com.wanted.wantedshop.product.application.ProductService;
import com.wanted.wantedshop.product.model.dto.request.ProductSaveRequest;
import com.wanted.wantedshop.product.model.entity.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class CommandProductController {
    public final ProductService service;

    @PostMapping
    public ResponseEntity<ResponseHandler<Long>> saveProduct(@RequestBody ProductSaveRequest saveRequest) {
        Long resultCount = service.saveProduct(saveRequest);
        return ResponseEntity.ok(
            ResponseHandler.of(
                    true,
                    resultCount,
                    ResultCode.SUCCESS_PRODUCTS.getResultMessage()
            )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseHandler<Long>> updateProduct(@PathVariable("id") Long id, @RequestBody ProductSaveRequest saveRequest) {
        return ResponseEntity.ok(
            ResponseHandler.of(
                true,
                service.updateProduct(id, saveRequest),
                ResultCode.SUCCESS.getResultMessage()
            )
        );
    }
}
