package com.example.projectJavaEE.Service;

import com.example.projectJavaEE.DTO.Request.OrdersRequest;
import com.example.projectJavaEE.DTO.Request.OrdersUpdateRequest;
import com.example.projectJavaEE.DTO.Response.OrdersResponse;
import com.example.projectJavaEE.Entites.*;
import com.example.projectJavaEE.Repository.CartRepo;
import com.example.projectJavaEE.Repository.OrdersRepo;
import com.example.projectJavaEE.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList; // ✅ Thêm import này
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    CartRepo repo;
    OrderMapper mapper;
    OrdersRepo ordersRepo;

    @Transactional
    public OrdersResponse createNew(int idAccount, OrdersRequest request) {
        Cart cart = repo.findByLogin_IdAccount(idAccount)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng của người dùng!"));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống rỗng, không thể tiến hành chốt đơn thanh toán!");
        }
        Orders orders = mapper.toOrder(request);

        orders.setLogin(cart.getLogin());
        orders.setPayment(request.getPaymentMethod());

        orders.setOrderDetailList(new ArrayList<>());

        double totalPriceCalculated = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            OrderDetail detail = new OrderDetail();

            detail.setOrders(orders);
            detail.setProduct(cartItem.getProduct());
            detail.setQuantity(cartItem.getQuantity());

            double price = cartItem.getProduct().getPrice();
            detail.setPriceAtOrder(price);

            totalPriceCalculated += (price * cartItem.getQuantity());
            orders.getOrderDetailList().add(detail);
        }

        orders.setTotalPrice(totalPriceCalculated);

        Orders saveOrders = ordersRepo.save(orders);

        cart.getCartItems().clear();
        repo.save(cart);

        return mapper.toOrdersResponse(saveOrders);
    }
    public List<OrdersResponse> getAll(){
        List<Orders> list = ordersRepo.findAll();
        return list.stream().map(mapper::toOrdersResponse).toList();
    }

    public OrdersResponse updateOrder(int id, OrdersUpdateRequest  request){
        Orders order = ordersRepo.findById(id).orElseThrow();
        order.setFullName(request.getFullName());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setPayment(request.getPaymentMethod());
        order.setStatus(request.getStatus());

        ordersRepo.save(order);

        OrdersResponse response = new OrdersResponse();
        response.setFullName(order.getFullName());
        response.setPhone(order.getPhone());
        response.setAddress(order.getAddress());
        response.setPayment(order.getPayment());
        response.setStatus(order.getStatus());

        return response;
    }

    public List<OrdersResponse> getMyOrders(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        int idAccount = Integer.parseInt(id);
        List<Orders> orders = ordersRepo.getOrdersByLogin_IdAccount(idAccount);
        return orders.stream().map(mapper::toOrdersResponse).toList();
    }
    @Transactional
    public String cancelOrder(int orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        int idAccount = Integer.parseInt(id);
        Orders order = ordersRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng mã #" + orderId));

        order.setStatus("Da Huy");

        ordersRepo.save(order);
        return "Đã hủy đơn hàng #" + orderId + " thành công và hoàn trả sản phẩm về kho!";
    }
}