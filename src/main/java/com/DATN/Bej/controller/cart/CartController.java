package com.DATN.Bej.controller.cart;

import com.DATN.Bej.dto.request.ApiResponse;
import com.DATN.Bej.dto.response.cart.CartItemResponse;
import com.DATN.Bej.service.guest.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    @PostMapping("/add/{attId}")
    ApiResponse<CartItemResponse> addToCart(@PathVariable String attId){
        return ApiResponse.<CartItemResponse>builder()
                .result(cartService.addToCart(attId))
                .build();
    }

    @GetMapping("/view")
    ApiResponse<List<CartItemResponse>> viewCart(){
        return ApiResponse.<List<CartItemResponse>>builder()
                .result(cartService.viewCart())
                .build();
    }

}