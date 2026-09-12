package com.example.projectJavaEE.mapper;

import com.example.projectJavaEE.DTO.Request.OrdersDetailRequest;
import com.example.projectJavaEE.DTO.Request.OrdersRequest;
import com.example.projectJavaEE.DTO.Response.OrdersDetailResponse;
import com.example.projectJavaEE.Entites.OrderDetail;
import com.example.projectJavaEE.Entites.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface OrdersDetailMapper {

    @Mapping(target = "product.id", source = "idProduct")
    @Mapping(target = "orders.idOrder" ,source = "idOrder")
    OrderDetail toOrderDetail(OrdersDetailRequest request);

    @Mapping(target = "idOrder",source = "orders.idOrder")
    @Mapping(target = "idProduct",source = "product.id")
    @Mapping(target = "namePro", source = "product.namePro")
    @Mapping(target = "price", source = "priceAtOrder")
    @Mapping(target = "image" , source = "product.image")
    OrdersDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
