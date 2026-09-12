package com.example.projectJavaEE.Entites;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
            @Column(name = "idCate")
    int idCate;
    String nameCate;

}
