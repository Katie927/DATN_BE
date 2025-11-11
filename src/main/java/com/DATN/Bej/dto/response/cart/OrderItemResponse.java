package com.DATN.Bej.dto.response.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {

    String img;

    String productAttName;
    int quantity;
    double price;
    String color;
    String productName;

}
