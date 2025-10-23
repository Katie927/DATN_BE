package com.DATN.Bej.dto.response.guest;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailRes {

    String id;
    String name;
    String image;

    int status;

    List<ProductImgRes> introImages;
    List<ProductVariantRes> variants;

}
