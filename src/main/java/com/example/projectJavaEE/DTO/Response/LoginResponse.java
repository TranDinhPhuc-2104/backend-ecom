package com.example.projectJavaEE.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    int idAccount;
    String gmail;
    String password;
    String role;
}
