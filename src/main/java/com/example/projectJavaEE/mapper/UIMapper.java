package com.example.projectJavaEE.mapper;

import com.example.projectJavaEE.DTO.Request.UIRequest;
import com.example.projectJavaEE.DTO.Response.UIResponse;
import com.example.projectJavaEE.Entites.UI;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper(componentModel = "spring")
public interface UIMapper {

    @Mapping(target = "login.idAccount",source = "idAccount")
    public UI toUI(UIRequest request);

    @Mapping(target = "idAccount", source = "login.idAccount")
    public UIResponse toUIResponse(UI ui);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login", ignore = true)
    public void toUIUpdate(@MappingTarget UI ui,UIRequest request);

}
