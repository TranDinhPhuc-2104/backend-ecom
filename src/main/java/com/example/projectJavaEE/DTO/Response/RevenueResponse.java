package com.example.projectJavaEE.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueResponse {
    private Double dailyRevenue;
    private Double monthlyRevenue;
    private Double yearlyRevenue;
    private List<ChartData> chartData; // Dữ liệu cho 7 ngày
}