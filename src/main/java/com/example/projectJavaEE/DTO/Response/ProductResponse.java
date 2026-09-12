package com.example.projectJavaEE.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    int id;
    String namePro;
    int quantity;
    int idCate;
    String description;
    String image;
    double price;
}
