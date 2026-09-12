package com.example.projectJavaEE.Service;


import com.example.projectJavaEE.DTO.Request.OrdersDetailRequest;
import com.example.projectJavaEE.DTO.Response.OrdersDetailResponse;
import com.example.projectJavaEE.DTO.Response.OrdersResponse;
import com.example.projectJavaEE.Entites.OrderDetail;
import com.example.projectJavaEE.Entites.Orders;
import com.example.projectJavaEE.Repository.CartItemRepo;
import com.example.projectJavaEE.Repository.OrderDetailRepo;
import com.example.projectJavaEE.Repository.OrdersRepo;
import com.example.projectJavaEE.mapper.OrdersDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailService {
    CartItemRepo repo;
    OrdersDetailMapper mapper;
    OrdersRepo ordersRepo;
    OrderDetailRepo orderDetailRepo;

   public List<OrdersDetailResponse> getByOrderId(int id){
        List<OrderDetail> list = orderDetailRepo.findByOrders_IdOrder(id);
        return list.stream().map(mapper::toOrderDetailResponse).toList();

   }

}
