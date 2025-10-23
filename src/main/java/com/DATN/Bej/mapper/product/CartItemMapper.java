package com.DATN.Bej.mapper.product;

import com.DATN.Bej.dto.response.cart.CartItemResponse;
import com.DATN.Bej.entity.cart.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "productAttName", source = "productA.name")
    CartItemResponse toCartItemResponse(CartItem cartItem);

}
