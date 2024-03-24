package com.example.crud.repositories;

import com.example.crud.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories("com.example.crud.repositories.CategoryRepository")
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
