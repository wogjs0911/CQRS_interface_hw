package com.wanted.wantedshop.product.presentation;

import com.wanted.wantedshop.common.domain.ResponseHandler;
import com.wanted.wantedshop.common.exception.ResultCode;
import com.wanted.wantedshop.product.application.ProductService;
import com.wanted.wantedshop.product.model.dto.request.ProductSearchRequest;
import com.wanted.wantedshop.product.model.dto.response.ProductSearchResponse;
import com.wanted.wantedshop.product.model.entity.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class QueryProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<ResponseHandler<Page<ProductSearchResponse>>>
    getProductsByConditions(@RequestBody ProductSearchRequest searchRequest) {
        Page<ProductSearchResponse> productList = service.getProductsByConditions(searchRequest);
        return ResponseEntity.ok(
                ResponseHandler.of(
                        true,
                        productList,
                        ResultCode.SUCCESS_PRODUCTS.getResultMessage()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHandler<Product>> getListById(@PathVariable Long id) {
        return ResponseEntity
                .ok(ResponseHandler.of(true, service.getListById(id), ResultCode.SUCCESS.getResultMessage()));
    }

    @GetMapping("/name")
    public Product getByName(String name) {
        return service.getByName(name);
    }

    @GetMapping("/list")
    public List<Product> getAllProductList() {
        return service.getAllProductList();
    }
}
