package com.example.projectJavaEE.Controller;

import com.example.projectJavaEE.DTO.Request.ProductRequest;
import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.DTO.Response.ProductResponse;
import com.example.projectJavaEE.Repository.CategoryRepo;
import com.example.projectJavaEE.Repository.ProductRepo;
import com.example.projectJavaEE.Service.ProductService;
import com.example.projectJavaEE.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/admin/product")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AdminProductController {
    ProductService service;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(service.createProduct(request))
                .build();
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(service.getAllProductAdmin())
                .build();
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("{id}")
    public ApiResponse<Void> deletePro(@PathVariable int id){
        service.deletePro(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .build();
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/update/{id}")
    public ApiResponse<ProductResponse> updateProductResponse(@PathVariable int id,@RequestBody ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(service.updateProductResponse(id,request))
                .build();
    }
}
