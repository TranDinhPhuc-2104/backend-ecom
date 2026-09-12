package com.example.projectJavaEE.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UIRequest {
    Integer id;
    String firstName;
    String lastName;
    String phone;
    Date dob;
    String address;
    Integer idAccount;
}
