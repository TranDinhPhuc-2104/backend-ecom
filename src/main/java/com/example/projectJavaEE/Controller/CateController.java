package com.example.projectJavaEE.Controller;


import com.example.projectJavaEE.DTO.Request.CateRequest;
import com.example.projectJavaEE.DTO.Response.CateResponse;
import com.example.projectJavaEE.Service.CateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/cate")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)

public class CateController {
    CateService service;

    @PostMapping()
    public CateResponse createCate(@RequestBody CateRequest request){
        return service.createCate(request);
    }

    @GetMapping()
    public List<CateResponse> getAllCate(){
        return service.getAllCate();
    }
}
