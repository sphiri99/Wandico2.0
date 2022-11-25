package com.wandico.service;

import com.wandico.entity.Product;
import com.wandico.repo.ProductRepo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductService {
    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void saveProduct(Optional<Product>  product) {
        productRepo.save(product.get());

    }

}
