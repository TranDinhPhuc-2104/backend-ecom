package com.example.projectJavaEE.Entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ui")
public class UI {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String firstName;
    String lastName;
    Date dob;
    String address;
    String phone;
    @OneToOne
    @JoinColumn(name = "idAccount")
    Login login;
}
