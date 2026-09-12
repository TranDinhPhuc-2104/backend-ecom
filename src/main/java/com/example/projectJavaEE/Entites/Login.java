package com.example.projectJavaEE.Entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Login {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idAccount;
    String gmail;
     String password;
     String role;
}
