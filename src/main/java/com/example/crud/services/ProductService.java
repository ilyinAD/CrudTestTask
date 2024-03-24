package com.example.crud.services;

import com.example.crud.entities.Product;
import com.example.crud.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> list() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

}