package com.DATN.Bej.dto.request.cartRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {

    String productAttId;
    String cartItemId;

    int quantity;
}
