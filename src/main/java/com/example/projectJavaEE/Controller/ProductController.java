package com.example.projectJavaEE.Controller;


import com.example.projectJavaEE.DTO.Request.ProductRequest;
import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.DTO.Response.PageResponse;
import com.example.projectJavaEE.DTO.Response.ProductResponse;
import com.example.projectJavaEE.Service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)

public class ProductController {
    ProductService service;

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(service.createProduct(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<ProductResponse>> getAllProduct(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "4") int size
    ) {
        return ApiResponse.<PageResponse<ProductResponse>>builder()
                .result(service.getAllProductPageable(page, size))
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable int id){
        return ApiResponse.<ProductResponse>builder()
                .result(service.getById(id))
                .build();
    }


    @GetMapping("/cate/{id}")
    public ApiResponse<List<ProductResponse>> getProductBy_IdCate(@PathVariable int id){
        return ApiResponse.<List<ProductResponse>> builder()
                .result(service.getProductBy_IdCate(id))
                .build();
    }

    @GetMapping("/namepro/{namePro}")
    public ApiResponse<List<ProductResponse>> getProductByNamePro(@PathVariable String namePro){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(service.getProductByNamePro(namePro))
                .build();
    }

    @GetMapping("/sort")
    public ApiResponse<PageResponse<ProductResponse>> getAllProductSorted(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "6") int size,
            @RequestParam(value = "sort", defaultValue = "featured") String sort // ✅ THÊM DÒNG NÀY
    ) {
        return ApiResponse.<PageResponse<ProductResponse>>builder()
                .result(service.getAllProductPageAbleSort(page, size, sort)) // Truyền tham số sort xuống Service
                .build();
    }
}
