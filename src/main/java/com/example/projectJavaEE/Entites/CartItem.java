package com.example.projectJavaEE.Entites;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCI;

    @ManyToOne
    @JoinColumn(name = "idCart", referencedColumnName = "idCart")
    @JsonIgnoreProperties("cartItems") // Chặn vòng lặp vô hạn khi Jackson parse JSON
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "id")// Khớp với trường id dưới DB
    private Product product;

    private double price;
    private Integer quantity;
}
