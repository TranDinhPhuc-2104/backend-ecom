package com.example.projectJavaEE.Entites;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String namePro;
    int quantity;
    @ManyToOne
    @JoinColumn(name = "idCate")
    Category category;

    double price;

    String description;
    String image;

    public Product(String namePro, int quantity, Category category, String description, String image, double price) {
        this.namePro = namePro;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
        this.image = image;
        this.price = price;
    }

}
