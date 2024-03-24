package com.example.crud.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
}
