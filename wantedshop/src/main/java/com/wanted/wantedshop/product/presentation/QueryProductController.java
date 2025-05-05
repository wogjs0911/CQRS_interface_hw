package com.wanted.wantedshop.product.presentation;

import com.wanted.wantedshop.product.application.ProductService;
import com.wanted.wantedshop.product.model.entity.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class QueryProductController {
    private final ProductService service;

    @GetMapping
    public Product getListById(Long id) {
        return service.getListById(id);
    }
    @GetMapping("/name")
    public Product getByTitle(String name) {
        return service.getByName(name);
    }
    @GetMapping("/list")
    public List<Product> getAllProductList() {
        return service.getAllProductList();
    }
}
