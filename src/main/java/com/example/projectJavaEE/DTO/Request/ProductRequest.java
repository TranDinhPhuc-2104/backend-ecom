package com.example.projectJavaEE.DTO.Request;

import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductRequest {
    String namePro;
    int quantity;
    int idCate;
    String description;
    String image;
    double price;

}
