package com.DATN.Bej.mapper;

import com.DATN.Bej.dto.request.productRequest.ProductVariantRequest;
import com.DATN.Bej.dto.response.productResponse.ProductVariantResponse;
import com.DATN.Bej.entity.product.ProductImage;
import com.DATN.Bej.entity.product.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    @Mapping(target = "detailImages", ignore = true)
    ProductVariant toVariant(ProductVariantRequest request);

    //    @Mapping(source = "id", target = "id")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "attributes", target = "attributes")
    ProductVariantResponse toVariantResponse(ProductVariant variant);

    List<ProductVariantResponse> toVariantResponseList(List<ProductVariant> variants);

    default  List<String> mapImages(List<ProductImage> images) {
        return images != null ?
                images.stream().map(ProductImage::getUrl).collect(Collectors.toList())
                : null;
    }

//    default List<ProductAttributeResponse> mapAttributes(List<VariantAttributeValue> values){
//        return values != null ?
//                values.stream().map(v -> new ProductAttributeResponse(
//                        v.getAttributeValue().getAttribute().getName(),
//                        v.getAttributeValue().getValue()
//                ))
//                        .toList() : null;
//    }
}
