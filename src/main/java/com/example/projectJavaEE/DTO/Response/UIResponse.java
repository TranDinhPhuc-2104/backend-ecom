package com.example.projectJavaEE.DTO.Response;


import com.example.projectJavaEE.Entites.Login;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UIResponse {
    int id;
    String firstName;
    String lastName;
    Date dob;
    String phone;
    String address;
   int idAccount;
}
