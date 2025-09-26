package com.DATN.Bej.dto.request.productRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeRequest {

    String id;
    String name;
//    String value;

    int originalPrice;
    int finalPrice;
    int discount;

    int stockQuantity;
    int soldQuantity;

}
