package com.example.projectJavaEE.Controller;

import com.example.projectJavaEE.DTO.Request.CateRequest;
import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.DTO.Response.CateResponse;
import com.example.projectJavaEE.Entites.Cart;
import com.example.projectJavaEE.Service.CartService;
import com.example.projectJavaEE.Service.CateService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cate")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")

public class CateAdminController {

    CateService service;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping()
    public CateResponse createCate(@RequestBody CateRequest request){
        return service.createCate(request);
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping()
    public List<CateResponse> getAllCate(){
        return service.getAllCate();
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("cate/{id}")
    public ApiResponse<CateResponse> updateCate(@PathVariable int id, @RequestBody CateRequest request){
        return ApiResponse.<CateResponse>builder()
                .result(service.updateCate(id,request))
                .build();
    }

//    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("catedelete/{id}")
    public ApiResponse<Void> deleteCate(@PathVariable int id){
        service.deleteCate(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("success")
                .build();
    }
}