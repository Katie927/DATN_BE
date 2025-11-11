package com.DATN.Bej.dto.response.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailsResponse {

    String id;

    String userName;

    String phoneNumber;
    String email;
    String address;
    LocalDate updatedAt;
    LocalDate orderAt;

    String description;

    double totalPrice;

    List<OrderItemResponse> orderItems = new ArrayList<>();
}
