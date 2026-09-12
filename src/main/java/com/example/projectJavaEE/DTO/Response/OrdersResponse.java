package com.example.projectJavaEE.DTO.Response;

import com.example.projectJavaEE.DTO.Request.OrdersDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    Integer idOrder;
    private Integer idAccount;
    private String fullName;
    private String phone;
    private String address;
    private String payment;
    String status;
    double totalPrice;
    Date orderDate;
}

