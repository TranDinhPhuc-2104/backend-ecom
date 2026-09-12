package com.example.projectJavaEE.mapper;

import com.example.projectJavaEE.DTO.Request.ProductRequest;
import com.example.projectJavaEE.DTO.Response.ProductResponse;
import com.example.projectJavaEE.Entites.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface ProductMapper {

//    @Mapping(target = "category" ,source = "idCate")
//    Product toProduct(ProductRequest request);


    @Mapping(target = "idCate", source = "category.idCate")

    ProductResponse toProductResponse(Product product);

}
