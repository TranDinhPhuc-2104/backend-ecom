package com.example.projectJavaEE.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartData {
    private String date;
    private Double revenue;
}