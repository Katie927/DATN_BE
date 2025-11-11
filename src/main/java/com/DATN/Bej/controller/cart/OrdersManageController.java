package com.DATN.Bej.controller.cart;

import com.DATN.Bej.dto.request.ApiResponse;
import com.DATN.Bej.dto.response.cart.OrderDetailsResponse;
import com.DATN.Bej.dto.response.cart.OrdersResponse;
import com.DATN.Bej.service.guest.CartService;
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
@RequestMapping("/manage/orders")
public class OrdersManageController {

    CartService cartService;

    @GetMapping("/get-all")
    ApiResponse<List<OrdersResponse>> getAllOrders(){
        return ApiResponse.<List<OrdersResponse>>builder()
                .result(cartService.getAllOrders())
                .build();
    }

    @GetMapping("/details/{orderId}")
    ApiResponse<OrderDetailsResponse> getOrderDetails(@PathVariable String orderId){
        return ApiResponse.<OrderDetailsResponse>builder()
                .result(cartService.getOrderDetails(orderId))
                .build();
    }

}
