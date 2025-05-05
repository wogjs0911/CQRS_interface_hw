package com.wanted.wantedshop.product.application;

import com.wanted.wantedshop.common.exception.ResultCode;
import com.wanted.wantedshop.common.exception.ServiceException;
import com.wanted.wantedshop.product.infrastructure.repository.ProductRepository;
import com.wanted.wantedshop.product.model.entity.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product getListById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
    }

    public Product getByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ServiceException(ResultCode.VALID_NOT_NULL));
    }

    public List<Product> getAllProductList() {
        List<Product> productList = repository.findAll();
        if (productList.isEmpty()) {
            throw new ServiceException(ResultCode.VALID_NOT_NULL);
        }
        return productList;
    }
}
