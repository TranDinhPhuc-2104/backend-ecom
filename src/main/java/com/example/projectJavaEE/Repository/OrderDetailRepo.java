package com.example.projectJavaEE.Repository;

import com.example.projectJavaEE.Entites.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrders_IdOrder(int id);

}
