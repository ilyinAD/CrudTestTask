package com.example.crud.repositories;

import com.example.crud.entities.Product;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByDeletedFalse();

    Optional<Product> findByIdAndDeletedFalse(UUID id);

    List<Product> findAllByCategoryId(UUID id);
}