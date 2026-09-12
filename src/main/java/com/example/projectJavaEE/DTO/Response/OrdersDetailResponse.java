package com.example.projectJavaEE.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetailResponse {
    int idOD;
    int idOrder;
    int idProduct;
    String namePro;
    String image;
    int quantity;
    double price;
}
