package com.example.projectJavaEE.Entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idOrder;

    String fullName;
    String phone;
    String address;

    @Column(name = "payment", nullable = false)
    String payment;

    double totalPrice;

    @OneToOne
    @JoinColumn(name = "idAccount")
    Login login;
    String status = "Dang xu ly";
    Date orderDate = new Date();

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetailList;
}
