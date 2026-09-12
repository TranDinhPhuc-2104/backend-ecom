package com.example.projectJavaEE.Controller;

import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.DTO.Response.RevenueResponse;
import com.example.projectJavaEE.Service.RevenueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/revenue")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RevenueController {
    RevenueService service;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    public ApiResponse<RevenueResponse> getRevenue(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "type", defaultValue = "date") String type) {

        return ApiResponse.<RevenueResponse>builder()
                .result(service.getRevenueOverview(date, type))
                .build();
    }
}