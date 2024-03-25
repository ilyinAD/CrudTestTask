package com.example.crud.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.id.factory.IdentifierGeneratorFactory;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String article;
    private String description;
    @ManyToOne()
    //@JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    private Float price;
    private Integer quantity;
    private Date created_date;
    private Date updated_date;
    private Boolean deleted = false;

    public Product(String title, String article, String description, Category category, Float price, Integer quantity, Date created_date, Date updated_date) {
        this.title = title;
        this.article = article;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public Product() {

    }
}
