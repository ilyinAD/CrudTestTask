package com.example.crud.controllers;

import com.example.crud.entities.Category;
import com.example.crud.repositories.CategoryRepository;
import com.example.crud.services.CategoryService;
import com.example.crud.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;


    @PostMapping(path="/add")
    public @ResponseBody String addNewCategory (@RequestParam String title) {

        Category category = new Category();
        category.setTitle(title);
//        AnnotationConfigApplicationContext context
//                = new AnnotationConfigApplicationContext();
//        context.scan("com.example.crud");
//        context.refresh();
//
//        CategoryService categoryService
//                = context.getBean(CategoryService.class);
//        categoryService.save(category);
//        context.close();
        categoryRepository.save(category);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Category> getAllCategories() {
        // This returns a JSON or XML with the users
        return categoryRepository.findAll();
    }
}