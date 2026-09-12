package com.example.projectJavaEE.Repository;

import com.example.projectJavaEE.Entites.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByLogin_IdAccount(int idAccount);

}
