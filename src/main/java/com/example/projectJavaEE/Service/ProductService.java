package com.example.projectJavaEE.Service;

import com.example.projectJavaEE.DTO.Request.ProductRequest;
import com.example.projectJavaEE.DTO.Response.PageResponse;
import com.example.projectJavaEE.DTO.Response.ProductResponse;
import com.example.projectJavaEE.Entites.Category;
import com.example.projectJavaEE.Entites.Product;
import com.example.projectJavaEE.Repository.CategoryRepo;
import com.example.projectJavaEE.Repository.ProductRepo;
import com.example.projectJavaEE.mapper.ProductMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductService {
    ProductRepo productRepo;
    ProductMapper mapper;
    CategoryRepo categoryRepo;

    public ProductResponse createProduct(ProductRequest request){
        String name = request.getNamePro();
        int quantity = request.getQuantity();
        String description = request.getDescription();
        String image = request.getImage();
        double price = request.getPrice();

        Category category = new Category();
        category.setIdCate(request.getIdCate());

        Product product = new Product(name,quantity,category,description,image, price);


        productRepo.save(product);

        return mapper.toProductResponse(product);
    }


    public List<ProductResponse> getAllProduct(){
        List<Product> products = productRepo.findAll();
        return products.stream().map(mapper::toProductResponse).toList();
    }

    public List<ProductResponse> getAllProductAdmin(){
        List<Product> products = productRepo.findAll();
        return products.stream().map(mapper::toProductResponse).toList();
    }

    public ProductResponse getById(int id){
        Product product = productRepo.findById(id).orElseThrow(()-> new RuntimeException("KO TIM THAY"));

        return mapper.toProductResponse(product);
    }
    public PageResponse<ProductResponse> getAllProductPageable(int page, int size){
        Pageable pageable = PageRequest.of(page -1 , size);
        Page<Product> pageData = productRepo.findAll(pageable);

        List<ProductResponse> productResponseList = pageData.getContent().stream()
                .map(mapper::toProductResponse)
                .toList();

        return PageResponse.<ProductResponse>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .pageSize(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .data(productResponseList)
                .build();
    }

    public List<ProductResponse> getProductBy_IdCate(int id){
        List<Product> productList = productRepo.getProductsByCategory_IdCate(id);
        return productList.stream().map(mapper::toProductResponse).toList();
    }

    public List<ProductResponse> getProductByNamePro(String name){
        List<Product> productList = productRepo.findByNameProContainingIgnoreCase(name);
        return productList.stream().map(mapper::toProductResponse).toList();
    }

    public ProductResponse updateProductResponse(int id,ProductRequest productRequest){
        Product product = productRepo.findById(id).orElseThrow(()->new RuntimeException("ko tim thay"));

        product.setNamePro(productRequest.getNamePro());
        product.setDescription(productRequest.getDescription());

        Category category = categoryRepo.findById(productRequest.getIdCate()).orElseThrow(()->new RuntimeException("errors"));

        product.setCategory(category);
        product.setPrice(productRequest.getPrice());
        product.setImage(productRequest.getImage());
        product.setQuantity(productRequest.getQuantity());

        productRepo.save(product);

        ProductResponse response = new ProductResponse();
        response.setDescription(product.getDescription());
        response.setIdCate(product.getCategory().getIdCate());
        response.setPrice(product.getPrice());
        response.setImage(product.getImage());
        response.setQuantity(product.getQuantity());
        response.setNamePro(product.getNamePro());

        return  response;
    }

    public void deletePro(int id){
        productRepo.deleteById(id);
    }


    public PageResponse<ProductResponse> getAllProductPageAbleSort(int page, int size, String sortDirection){
        Sort sort = Sort.by("id").ascending();

        if("lowToHigh".equalsIgnoreCase(sortDirection)){
            sort = Sort.by("price").ascending();
        } else if ("highToLow".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by("price").descending();
        }

        Pageable pageable = PageRequest.of(page -1 , size, sort);
        var pageData = productRepo.findAll(pageable);

        List<ProductResponse> productResponseList = pageData.getContent().stream()
                .map(mapper::toProductResponse)
                .toList();

        return PageResponse.<ProductResponse>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .pageSize(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .data(productResponseList)
                .build();
    }
}
