package com.example.projectJavaEE.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetailRequest {
    int idOrder;
   String namePro;
    int idProduct;
    int quantity;
}
