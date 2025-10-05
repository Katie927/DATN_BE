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

    String id;
    String color;

    List<ProductImageRequest> detailImages;
    List<ProductAttributeRequest> attributes;

}
