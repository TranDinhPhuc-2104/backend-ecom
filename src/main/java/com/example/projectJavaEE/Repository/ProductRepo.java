package com.example.projectJavaEE.Repository;

import com.example.projectJavaEE.DTO.Response.ProductResponse;
import com.example.projectJavaEE.Entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> getProductsByCategory_IdCate(int idCate);

    List<Product> findByNameProContainingIgnoreCase(String name);

}
