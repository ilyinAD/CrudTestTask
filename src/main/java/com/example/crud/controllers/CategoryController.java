package com.example.crud.controllers;

import com.example.crud.entities.Category;
import com.example.crud.entities.Product;
import com.example.crud.errors.MyError;
import com.example.crud.errors.SQLORMException;
import com.example.crud.repositories.CategoryRepository;
import com.example.crud.repositories.ProductRepository;
import com.example.crud.services.CategoryService;
import com.example.crud.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @PostMapping(path="/add")
    public @ResponseBody String addNewCategory(@RequestParam String title) {
        Category category = new Category(title);
        categoryRepository.save(category);
        return String.valueOf(new ResponseEntity<>(category, HttpStatus.OK));
    }
    @PutMapping(path="/update")
    public @ResponseBody ResponseEntity<?> updateCategory(@RequestParam UUID id,
                                              @RequestParam(required = false) String title,
                                              @RequestParam(required = false) String article,
                                              @RequestParam(required = false) String description,
                                              @RequestParam(required = false) UUID category_id,
                                              @RequestParam(required = false) Float price,
                                              @RequestParam(required = false) Integer quantity) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(id);
            Category category = optionalCategory.orElse(null);
            if (category == null) {
                throw new EntityNotFoundException("Category with id = %s was not found".formatted(id));
            }
            if (title != null)
                category.setTitle(title);
            categoryRepository.save(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(path="/permdelete")
    public @ResponseBody ResponseEntity<?> permanentDeleteCategory(@RequestParam UUID id) {
        try {
            if (!productRepository.findAllByCategoryId(id).isEmpty()) {
                throw new SQLORMException("Category with id = %s uses in products".formatted(id));
            }
            Category category = categoryRepository.findById(id).orElse(null);
            if (category == null) {
                throw new EntityNotFoundException("Category with id = %s was not found".formatted(id));
            }
            categoryRepository.deleteById(id);
            return new ResponseEntity<>("deleted id = %s".formatted(id), HttpStatus.OK);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch(SQLORMException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(path="/delete")
    public @ResponseBody ResponseEntity<?> safeDeleteCategory(@RequestParam UUID id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(id);
            Category category = optionalCategory.orElse(null);
            if (category == null) {
                throw new EntityNotFoundException("Category with id = %s was not found".formatted(id));
            }
            category.setDeleted(true);
            categoryRepository.save(category);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
        catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path="/allNotDeleted")
    public @ResponseBody Iterable<Category> getAllNotDeletedCategories() {
        return categoryRepository.findAllByDeletedFalse();
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody ResponseEntity read(@RequestParam UUID id) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findByIdAndDeletedFalse(id);
            Category category = categoryOptional.orElse(null);
            if (category == null) {
                throw new EntityNotFoundException("Category with id = %s was not found".formatted(id));
            }
            category.setDeleted(true);
            categoryRepository.save(category);
            return new ResponseEntity(categoryRepository.getReferenceById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}