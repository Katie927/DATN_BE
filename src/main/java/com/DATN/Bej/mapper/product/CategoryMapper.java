package com.DATN.Bej.mapper.product;

import com.DATN.Bej.dto.request.productRequest.CategoryRequest;
import com.DATN.Bej.dto.response.productResponse.CategoryResponse;
import com.DATN.Bej.dto.response.productResponse.VariantSummaryResponse;
import com.DATN.Bej.entity.product.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductVariantMapper.class, VariantSummaryResponse.class})

public interface CategoryMapper {

    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryRequest request);

    @Mapping(source = "id", target = "id")
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toResponseList(List<Category> categories);

}
