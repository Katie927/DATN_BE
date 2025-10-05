package com.DATN.Bej.dto.response.productResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse {

    String id;
    String color;

    List<ProductImageResponse> detailImages;
    List<ProductAttributeResponse> attributes;

}
