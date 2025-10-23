package com.DATN.Bej.dto.response.productResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeResponse {

    String id;
    String name;
//    String value;

    double originalPrice;
    double finalPrice;
    double discount;

    int stockQuantity;
    int soldQuantity;

}
