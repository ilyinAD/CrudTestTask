package com.example.crud.controllers;

import com.example.crud.entities.Category;
import com.example.crud.entities.Product;
import com.example.crud.repositories.CategoryRepository;
import com.example.crud.repositories.ProductRepository;
import com.example.crud.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(path="/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @PostMapping(path="/add")
    public @ResponseBody String addNewProduct (@RequestParam String title,
                                               @RequestParam String article,
                                               @RequestParam String description,
                                               @RequestParam UUID category_id,
                                               @RequestParam Float price,
                                               @RequestParam Integer quantity) {
        Date created_date = new Date(System.currentTimeMillis());
        Date updated_date = new Date(System.currentTimeMillis());
        Product product  = new Product(title, article, description, categoryRepository.getReferenceById(category_id), price,
                quantity, created_date, updated_date);

//        AnnotationConfigApplicationContext context
//                = new AnnotationConfigApplicationContext();
//        context.scan("com.example.crud");
//        context.refresh();
//
//        ProductService productService
//                = context.getBean(ProductService.class);
//        productService.save(product);
//        context.close();
        productRepository.save(product);
        return "Saved";
    }

    @PostMapping(path="/del")
    public @ResponseBody String delProduct(@RequestParam UUID id) {
        productRepository.deleteById(id);
        return "Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
}