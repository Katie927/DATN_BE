package com.DATN.Bej.controller;


import com.DATN.Bej.dto.request.ApiResponse;
import com.DATN.Bej.dto.response.productResponse.ProductListResponse;
import com.DATN.Bej.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/")
public class ProductController {

    ProductService productService;

    @GetMapping
    ApiResponse<List<ProductListResponse>> getProducts() {
//        ApiResponse<List<Product>> apiResponse = new ApiResponse<>();
        return ApiResponse.<List<ProductListResponse>>builder()
                .result(productService.getProducts())
                .build();
    }

//    @PostMapping()
}
