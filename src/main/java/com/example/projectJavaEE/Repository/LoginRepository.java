package com.example.projectJavaEE.Repository;

import com.example.projectJavaEE.Entites.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
    Optional<Login> findByGmail(String gmail);
}
