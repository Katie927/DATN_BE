package com.DATN.Bej.mapper.product;

import com.DATN.Bej.dto.request.productRequest.ProductRequest;
import com.DATN.Bej.dto.response.guest.ProductDetailRes;
import com.DATN.Bej.dto.response.productResponse.ProductListResponse;
import com.DATN.Bej.dto.response.productResponse.ProductResponse;
import com.DATN.Bej.dto.response.productResponse.VariantSummaryResponse;
import com.DATN.Bej.entity.product.Product;
import com.DATN.Bej.entity.product.ProductVariant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductVariantMapper.class, VariantSummaryResponse.class})
public interface ProductMapper {
    //    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "introImages", ignore = true)
    Product toProduct(ProductRequest request);

    //    @Mapping(source = "status", target = "status")
    @Mapping(source = "id", target = "id")
    ProductResponse toProductResponse(Product product);
    ProductDetailRes toProductDetailResponse(Product product);
    List<ProductResponse> toResponseList(List<Product> products);


    @Mapping(target = "variant", expression = "java(firstVariantSummary(product.getVariants()))")
    ProductListResponse toProductListResponse(Product product);
    List<ProductListResponse> toListProduct(List<Product> products);

    default VariantSummaryResponse firstVariantSummary(List<ProductVariant> variants){
        if (variants == null || variants.isEmpty()) {
            return new VariantSummaryResponse(); // trả về object rỗng
        }
        ProductVariant v = variants.getFirst();
        VariantSummaryResponse summary = new VariantSummaryResponse();
        if(v != null && v.getDetailImages() != null && !v.getDetailImages().isEmpty()){
            summary.setOriginalPrice(v.getAttributes().getFirst().getOriginalPrice());
            summary.setFinalPrice(v.getAttributes().getFirst().getFinalPrice());
        }
//            summary.setThumbnail(v.getDetailImages().getFirst().getUrl());

        return summary;
    }

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "introImages", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduct(@MappingTarget Product product, ProductRequest request);

    // Chuyển danh sách ProductAttribute thành danh sách String (chỉ lấy giá trị)
//    default List<String> mapSpecs(List<ProductAttribute> attributes) {
//        return attributes != null ?
//                attributes.stream().map(ProductAttribute::getValue).collect(Collectors.toList())
//                : null;
//    }
//
//    default  List<String> mapImages(List<ProductImage> images) {
//        return images != null ?
//                images.stream().map(ProductImage::getUrl).collect(Collectors.toList())
//                : null;
//    }
}

