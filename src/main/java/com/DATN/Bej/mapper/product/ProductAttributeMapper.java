package com.DATN.Bej.mapper.product;

import com.DATN.Bej.dto.request.productRequest.ProductAttributeRequest;
import com.DATN.Bej.dto.response.productResponse.ProductAttributeResponse;
import com.DATN.Bej.entity.product.ProductAttribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {

    ProductAttribute toProductAttribute(ProductAttributeRequest request);

    ProductAttributeResponse toProductAttributeResponse(ProductAttribute attribute);
    List<ProductAttributeResponse> toAttributeList(List<ProductAttribute> attributes);

}
