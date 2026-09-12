package com.example.projectJavaEE.Controller;


import com.example.projectJavaEE.DTO.Request.UIRequest;
import com.example.projectJavaEE.DTO.Response.ApiResponse;
import com.example.projectJavaEE.DTO.Response.UIResponse;
import com.example.projectJavaEE.Service.UIService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ui")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class UIController {
    UIService service;

    @PostMapping
    public ApiResponse<UIResponse> creatUI(@RequestBody UIRequest request){
        return ApiResponse.<UIResponse>builder()
                .result(service.createUI(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UIResponse>> getAll(){
        return ApiResponse.<List<UIResponse>>builder()
                .result(service.getAllUi())
                .build();
    }

    @GetMapping("/myProfile")
    public ApiResponse<UIResponse> getMyProfile(){
        return ApiResponse.<UIResponse>builder()
                .result(service.getMyProfile())
                .build();
    }

    @PutMapping("/update")
    public ApiResponse<UIResponse> updateProfile(@RequestBody UIRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int id = Integer.parseInt(authentication.getName());

        return ApiResponse.<UIResponse>builder()
                .result(service.updateUI(id,request))
                .build();
    }
}
