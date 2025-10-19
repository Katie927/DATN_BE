package com.DATN.Bej.controller;


import com.DATN.Bej.dto.request.ApiResponse;
import com.DATN.Bej.dto.response.guest.ProductDetailRes;
import com.DATN.Bej.dto.response.productResponse.ProductListResponse;
import com.DATN.Bej.service.ProductService;
import com.DATN.Bej.service.guest.GuestProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/home")
public class ProductController {

    ProductService productService;
    GuestProductService guestProductService;

    @GetMapping
    ApiResponse<List<ProductListResponse>> getProducts() {
//        ApiResponse<List<Product>> apiResponse = new ApiResponse<>();
        return ApiResponse.<List<ProductListResponse>>builder()
                .result(productService.getProducts())
                .build();
    }

    @GetMapping("/product/{productId}")
    ApiResponse<ProductDetailRes> getProductDetails(@PathVariable String productId){
        return ApiResponse.<ProductDetailRes>builder()
                .result(guestProductService.getProductDetails(productId))
                .build();
    }

//    @PostMapping()
}
