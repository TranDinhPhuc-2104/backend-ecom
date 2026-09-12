package com.example.projectJavaEE.mapper;


import com.example.projectJavaEE.DTO.Request.LoginRequest;
import com.example.projectJavaEE.DTO.Response.LoginResponse;
import com.example.projectJavaEE.Entites.Login;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "Spring")
public interface LogInMapper {

     Login toLogin(LoginRequest loginRequest);

     LoginResponse toLoginResponse(Login login);
}
