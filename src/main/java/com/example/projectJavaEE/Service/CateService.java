package com.example.projectJavaEE.Service;

import com.example.projectJavaEE.DTO.Request.CateRequest;
import com.example.projectJavaEE.DTO.Response.CateResponse;
import com.example.projectJavaEE.Entites.Category;
import com.example.projectJavaEE.Repository.CategoryRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CateService {
    CategoryRepo categoryRepo;

    public CateResponse createCate(CateRequest request){
        Category category = new Category();
        category.setNameCate(request.getNameCate());

        categoryRepo.save(category);
        CateResponse response = new CateResponse();
        response.setNameCate(category.getNameCate());

        return response ;
    }


    public List<CateResponse> getAllCate(){
        var cate = categoryRepo.findAll();

        return cate.stream().map(category -> {
            CateResponse cateResponse = new CateResponse();
            cateResponse.setIdCate(category.getIdCate());
            cateResponse.setNameCate(category.getNameCate());
            return cateResponse;
        }).toList();
    }
    public void deleteCate(int id){
        categoryRepo.deleteById(id);
    }

    public CateResponse updateCate(int id, CateRequest request){
        Category category = categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("ko tim thay"));
        category.setNameCate(request.getNameCate());
        categoryRepo.save(category);

        CateResponse response = new CateResponse();
        response.setIdCate(category.getIdCate());
        response.setNameCate(category.getNameCate());
        return response;
    }
}
