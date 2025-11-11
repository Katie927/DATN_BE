package com.DATN.Bej.mapper.product;

import com.DATN.Bej.dto.response.cart.CartItemResponse;
import com.DATN.Bej.entity.cart.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "productAttName", source = "productA.name")
    @Mapping(target = "attId", source = "productA.id")
    @Mapping(target = "img", expression = "java(getSecondImageUrl(cartItem))")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    default String getSecondImageUrl(CartItem cartItem) {
        var images = cartItem.getProductA()
                .getVariant()
                .getDetailImages();
        if (images == null || images.size() <= 1 || images.get(1) == null) return null;
        return images.get(0).getUrl();
    }

}
