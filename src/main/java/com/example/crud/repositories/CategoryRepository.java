package com.example.crud.repositories;

import com.example.crud.entities.Category;
import com.example.crud.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories("com.example.crud.repositories.CategoryRepository")
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByDeletedFalse();
    Optional<Category> findByIdAndDeletedFalse(UUID id);
}
