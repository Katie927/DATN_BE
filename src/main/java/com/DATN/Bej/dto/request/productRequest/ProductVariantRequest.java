package com.DATN.Bej.dto.request.productRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantRequest {

    String color;

    int originalPrice;
    int finalPrice;
    int discount;

    int stockQty = 0;
    int soldQty = 0;

    List<MultipartFile> detailImages;

    List<ProductAttributeRequest> attributes;
}
