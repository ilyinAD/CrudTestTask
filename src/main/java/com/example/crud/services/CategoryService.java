package com.example.crud.services;

import com.example.crud.entities.Category;
import com.example.crud.entities.Product;
import com.example.crud.repositories.CategoryRepository;
import com.example.crud.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> list() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }
}