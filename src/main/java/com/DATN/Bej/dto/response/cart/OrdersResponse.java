package com.DATN.Bej.dto.response.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersResponse {

    String id;

    String userName;
    String address;

    int status;
    int type;

    double totalPrice;

}
