package com.example.crud.repositories;

import com.example.crud.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories("com.example.crud.repositories.ProductRepository")
public interface ProductRepository extends JpaRepository<Product, UUID> {
}