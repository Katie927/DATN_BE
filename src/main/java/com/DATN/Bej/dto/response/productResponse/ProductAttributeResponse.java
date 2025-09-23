package com.DATN.Bej.dto.response.productResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeResponse {

    String name;
//    String value;

    int originalPrice;
    int finalPrice;
    int discount;

    int stockQuantity;
    int soldQuantity;

}
