package com.example.projectJavaEE.mapper;

import com.example.projectJavaEE.DTO.Request.OrdersRequest;
import com.example.projectJavaEE.DTO.Response.OrdersResponse;
import com.example.projectJavaEE.Entites.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface OrderMapper {

    Orders toOrder(OrdersRequest request);

//    @Mapping(target = "ordersDetailRequestList",source = "orderDetailList")
    OrdersResponse toOrdersResponse(Orders orders);
}
