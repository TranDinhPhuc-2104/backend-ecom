package com.example.projectJavaEE.Entites;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idOD;

    @ManyToOne
    @JoinColumn(name = "idOrder")
    @JsonIgnoreProperties("orderDetailList")
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    Product product;

    int quantity;
    double priceAtOrder;
}
