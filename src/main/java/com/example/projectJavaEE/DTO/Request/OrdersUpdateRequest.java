package com.example.projectJavaEE.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersUpdateRequest {
   private String fullName;
   private String phone;
   private String address;
   private String paymentMethod;
   private String status;
}
