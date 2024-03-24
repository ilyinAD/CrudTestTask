package com.example.crud.controllers;

import com.example.crud.entities.Category;
import com.example.crud.entities.Product;
import com.example.crud.errors.MyError;
import com.example.crud.repositories.CategoryRepository;
import com.example.crud.repositories.ProductRepository;
import com.example.crud.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
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
                                               @RequestParam(required = false) String description,
                                               @RequestParam(required = false) UUID category_id,
                                               @RequestParam Float price,
                                               @RequestParam Integer quantity) {
        Date created_date = new Date(System.currentTimeMillis());
        Date updated_date = new Date(System.currentTimeMillis());
        Product product  = new Product(title, article, description, categoryRepository.getReferenceById(category_id), price,
                quantity, created_date, updated_date);
        productRepository.save(product);
        return String.valueOf(new ResponseEntity<>(product, HttpStatus.OK));
    }

    @PutMapping(path="/update")
    public @ResponseBody ResponseEntity<?> updateProduct(@RequestParam UUID id,
                                              @RequestParam(required = false) String title,
                                              @RequestParam(required = false) String article,
                                              @RequestParam(required = false) String description,
                                              @RequestParam(required = false) UUID category_id,
                                              @RequestParam(required = false) Float price,
                                              @RequestParam(required = false) Integer quantity) {
        try {
            Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
            Product product = optionalProduct.orElse(null);
            if (product == null) {
                throw new EntityNotFoundException("Product with id = %s not found".formatted(id));
            }
            if (title != null)
                product.setTitle(title);
            if (article != null)
                product.setArticle(article);
            if (description != null)
                product.setDescription(description);
            if (category_id != null) {
                Category category = categoryRepository.findById(id).orElse(null);
                if (category == null) {
                    throw new EntityNotFoundException("category_id = %s was not found".formatted(category_id));
                }
                product.setCategory(category);
            }
            if (price != null)
                product.setPrice(price);
            if (quantity != null)
                product.setQuantity(quantity);
            product.setUpdated_date(new Date(System.currentTimeMillis()));
            productRepository.save(product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/permdelete")
    public @ResponseBody ResponseEntity<?> permanentDeleteProduct(@RequestParam UUID id) {
        try {
            Product product = productRepository.findById(id).orElse(null);
            if (product == null) {
                throw new EntityNotFoundException("Product with id = %s was not found".formatted(id));
            }
            productRepository.deleteById(id);
            return new ResponseEntity<>("deleted id = %s".formatted(id), HttpStatus.OK);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody ResponseEntity<?> safeDeleteProduct(@RequestParam UUID id) {
        try {
            Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
            Product product = optionalProduct.orElse(null);
            if (product == null) {
                throw new EntityNotFoundException("Product with id = %s was not found".formatted(id));
            }
            product.setDeleted(true);
            productRepository.save(product);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/allNotDeleted")
    public @ResponseBody Iterable<Product> getAllNotDeletedProducts() {
        return productRepository.findAllByDeletedFalse();
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody ResponseEntity read(@PathVariable UUID id) {
        try {
            Optional<Product> optionalProduct = productRepository.findByIdAndDeletedFalse(id);
            Product product = optionalProduct.orElse(null);
            if (product == null) {
                throw new EntityNotFoundException("Product with id = %s was not found".formatted(id));
            }
            product.setDeleted(true);
            productRepository.save(product);
            return new ResponseEntity(productRepository.getReferenceById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}