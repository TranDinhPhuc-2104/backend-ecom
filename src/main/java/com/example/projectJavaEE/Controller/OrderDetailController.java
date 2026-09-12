package com.example.projectJavaEE.Controller;


import com.example.projectJavaEE.DTO.Request.OrdersDetailRequest;
import com.example.projectJavaEE.DTO.Request.OrdersRequest;
import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.DTO.Response.OrdersDetailResponse;
import com.example.projectJavaEE.DTO.Response.OrdersResponse;
import com.example.projectJavaEE.Entites.OrderDetail;
import com.example.projectJavaEE.Service.OrderDetailService;
import com.example.projectJavaEE.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderdetail")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class OrderDetailController {
    OrderDetailService service;

    @GetMapping("/{id}")
    public ApiResponse<List<OrdersDetailResponse>> getODByIdOrders(@PathVariable int id){
        return ApiResponse.<List<OrdersDetailResponse>>builder()
                .result(service.getByOrderId(id))
                .build();
    }
}
