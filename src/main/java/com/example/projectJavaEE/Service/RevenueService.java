package com.example.projectJavaEE.Service;

import com.example.projectJavaEE.DTO.Response.ChartData;
import com.example.projectJavaEE.DTO.Response.RevenueResponse;
import com.example.projectJavaEE.Repository.OrdersRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RevenueService {
    OrdersRepo ordersRepository;

    public RevenueResponse getRevenueOverview(LocalDate selectedDate, String type) {
        int year = selectedDate.getYear();
        int month = selectedDate.getMonthValue();
        int day = selectedDate.getDayOfMonth();

        Double daily = ordersRepository.getDailyRevenue(year, month, day);
        Double monthly = ordersRepository.getMonthlyRevenue(year, month);
        Double yearly = ordersRepository.getYearlyRevenue(year);

        List<ChartData> chartDataList = new ArrayList<>();

        if ("month".equalsIgnoreCase(type)) {
            List<Object[]> rawData = ordersRepository.getRevenueDailyInMonth(year, month);
            Map<Integer, Double> map = rawData.stream().collect(Collectors.toMap(
                    r -> ((Number) r[0]).intValue(),
                    r -> ((Number) r[1]).doubleValue()
            ));

            int lengthOfMonth = selectedDate.lengthOfMonth();
            for (int d = 1; d <= lengthOfMonth; d++) {
                Double rev = map.getOrDefault(d, 0.0);
                chartDataList.add(new ChartData("Ngày " + d, rev));
            }

        } else if ("year".equalsIgnoreCase(type)) {
            List<Object[]> rawData = ordersRepository.getRevenueMonthlyInYear(year);
            Map<Integer, Double> map = rawData.stream().collect(Collectors.toMap(
                    r -> ((Number) r[0]).intValue(),
                    r -> ((Number) r[1]).doubleValue()
            ));

            for (int m = 1; m <= 12; m++) {
                Double rev = map.getOrDefault(m, 0.0);
                chartDataList.add(new ChartData("Tháng " + m, rev));
            }

        } else {
            LocalDateTime startDateTime = selectedDate.minusDays(6).atStartOfDay();
            LocalDateTime endDateTime = selectedDate.atTime(23, 59, 59);

            List<Object[]> rawData = ordersRepository.getRevenueByDateRange(startDateTime, endDateTime);

            Map<LocalDate, Double> map = rawData.stream().collect(Collectors.toMap(
                    r -> LocalDate.of(((Number) r[0]).intValue(), ((Number) r[1]).intValue(), ((Number) r[2]).intValue()),
                    r -> ((Number) r[3]).doubleValue()
            ));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            for (int i = 6; i >= 0; i--) {
                LocalDate targetDate = selectedDate.minusDays(i);
                Double rev = map.getOrDefault(targetDate, 0.0);
                chartDataList.add(new ChartData(targetDate.format(formatter), rev));
            }
        }

        return RevenueResponse.builder()
                .dailyRevenue(daily)
                .monthlyRevenue(monthly)
                .yearlyRevenue(yearly)
                .chartData(chartDataList)
                .build();
    }
}