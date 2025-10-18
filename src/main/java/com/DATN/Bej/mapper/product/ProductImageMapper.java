package com.DATN.Bej.mapper.product;

import com.DATN.Bej.dto.response.productResponse.ProductImageResponse;
import com.DATN.Bej.entity.product.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImage toProductImage(ProductImageResponse request);

    @Mapping(source = "id", target = "id")
    ProductImageResponse toProductImageResponse(ProductImage image);

}
