package com.example.projectJavaEE.Controller;


import com.example.projectJavaEE.DTO.Request.AuthenticationRequest;
import com.example.projectJavaEE.DTO.Request.LoginRequest;
import com.example.projectJavaEE.DTO.Request.RefreshTokenRequest;
import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.DTO.Response.AuthenticationResponse;
import com.example.projectJavaEE.DTO.Response.LoginResponse;
import com.example.projectJavaEE.Entites.Login;
import com.example.projectJavaEE.Service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {
    private final ServletResponse servletResponse;
    AuthenticationService service;

    @PostMapping("/register")
    public ApiResponse<LoginResponse> createAccount(@RequestBody LoginRequest request){
        return ApiResponse.<LoginResponse>builder()
                .result(service.createAccount(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<LoginResponse>> getAllAccount(){
        return ApiResponse.<List<LoginResponse>>builder()
                .result(service.getAllAccount())
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> Login(@RequestBody AuthenticationRequest request, HttpServletResponse response){
        AuthenticationResponse authenticationResponse = service.Authen(request);

        Cookie cookie = new Cookie("accessToken", authenticationResponse.getToken());
        cookie.setPath("/");
        cookie.setMaxAge(30*60);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");

        response.addCookie(cookie);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
                .build();
    }

//    @PostMapping("/admin/login")
//    public ApiResponse<AuthenticationResponse> loginAdmin(@RequestBody AuthenticationRequest request, HttpServletResponse servletResponse){
//        AuthenticationResponse response = service.adminAuth(request);
//
//        Cookie cookie = new Cookie("accessToken",response.getToken());
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setMaxAge(30*60);
//        cookie.setPath("/");
//        cookie.setDomain("localhost");
//
//        servletResponse.addCookie(cookie);
//        return ApiResponse.<AuthenticationResponse>builder()
//                .result(response)
//                .build();
//    }

    @PostMapping("/admin/login")
    public ApiResponse<AuthenticationResponse> loginAdmin(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(service.adminAuth(request))
                .build();
    }
//    @PostMapping("/refresh")
//    public ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
//        return ApiResponse.<AuthenticationResponse>builder()
//                .result(service.refreshToken(request))
//                .build();
//    }
}
