package com.example.projectJavaEE.Controller;


import com.example.projectJavaEE.DTO.Request.OrdersRequest;
import com.example.projectJavaEE.DTO.Request.OrdersUpdateRequest;
import com.example.projectJavaEE.DTO.Request.UIRequest;
import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.DTO.Response.OrdersResponse;
import com.example.projectJavaEE.DTO.Response.UIResponse;
import com.example.projectJavaEE.Entites.Orders;
import com.example.projectJavaEE.Service.OrderService;
import com.example.projectJavaEE.Service.UIService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class OrdersController {
    OrderService service;

    @PostMapping("/create")
    public ApiResponse<OrdersResponse> creatUI(@RequestBody OrdersRequest request, @AuthenticationPrincipal Jwt jwt){
        int idAccount;
        if (jwt.getClaim("id") != null) {
            idAccount = Integer.parseInt(jwt.getClaim("id").toString());
        } else if (jwt.getClaim("idAccount") != null) {
            idAccount = Integer.parseInt(jwt.getClaim("idAccount").toString());
        } else {
            idAccount = Integer.parseInt(jwt.getSubject());
        }
        OrdersResponse response = service.createNew(idAccount, request);
        return ApiResponse.<OrdersResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/profile")
    public ApiResponse<List<OrdersResponse>> getAll(){
        return ApiResponse.<List<OrdersResponse>>builder()
                .result(service.getAll())
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<OrdersResponse> updateOrder(@PathVariable int id, @RequestBody OrdersUpdateRequest request){
        return ApiResponse.<OrdersResponse>builder()
                .result(service.updateOrder(id, request))
                .build();
    }

    @GetMapping("/getmyOrders")
    public ApiResponse<List<OrdersResponse>> getMyOrders(){
        return ApiResponse.<List<OrdersResponse>>builder()
                .result(service.getMyOrders())
                .build();
    }

    @PutMapping("/cancel/{id}")
    public ApiResponse<String> cancelOrder(@PathVariable("id") int id) {
        return ApiResponse.<String>builder()
                .result(service.cancelOrder(id)) // Gọi sang hàm cancelOrder trong Service
                .build();
    }
}
