package com.example.crud.controllers;

import com.example.crud.entities.Category;
import com.example.crud.errors.MyError;
import com.example.crud.exception.SQLORMException;
import com.example.crud.repositories.CategoryRepository;
import com.example.crud.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/category")
@Profile("production")
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

        Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(id);
        Category category = optionalCategory.orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("Category with id = %s was not found".formatted(id));
        }
        if (title != null)
            category.setTitle(title);
        categoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @DeleteMapping(path="/permdelete")
    public @ResponseBody ResponseEntity<?> permanentDeleteCategory(@RequestParam UUID id) throws SQLORMException {
        if (!productRepository.findAllByCategoryId(id).isEmpty()) {
            throw new SQLORMException("Category with id = %s uses in products".formatted(id));
        }
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("Category with id = %s was not found".formatted(id));
        }
        categoryRepository.deleteById(id);
        return new ResponseEntity<>("deleted id = %s".formatted(id), HttpStatus.OK);
    }
    @DeleteMapping(path="/delete")
    public @ResponseBody ResponseEntity<?> safeDeleteCategory(@RequestParam UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndDeletedFalse(id);
        Category category = optionalCategory.orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("Category with id = %s was not found".formatted(id));
        }
        category.setDeleted(true);
        categoryRepository.save(category);
        return new ResponseEntity<>(id, HttpStatus.OK);
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
    public @ResponseBody ResponseEntity<?> read(@PathVariable UUID id) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndDeletedFalse(id);
        Category category = categoryOptional.orElse(null);
        if (category == null) {
            throw new EntityNotFoundException("Category with id = %s was not found".formatted(id));
        }
        category.setDeleted(true);
        categoryRepository.save(category);
        return new ResponseEntity<>(categoryRepository.getReferenceById(id), HttpStatus.OK);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException e) {
        return new ResponseEntity<>(new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SQLORMException.class)
    public ResponseEntity<?> handleException(SQLORMException e) {
        return new ResponseEntity<>(new MyError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}