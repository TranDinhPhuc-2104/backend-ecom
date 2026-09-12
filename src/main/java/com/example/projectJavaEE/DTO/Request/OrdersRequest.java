package com.example.projectJavaEE.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersRequest {
   private Integer idAccount;
   private String fullName;
   private String phone;
   private String address;
   private String paymentMethod;

}
