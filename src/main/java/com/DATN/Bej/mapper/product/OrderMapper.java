package com.DATN.Bej.mapper.product;

import com.DATN.Bej.dto.request.cartRequest.OrderItemRequest;
import com.DATN.Bej.dto.request.cartRequest.OrderRequest;
import com.DATN.Bej.dto.response.cart.OrderDetailsResponse;
import com.DATN.Bej.dto.response.cart.OrderItemResponse;
import com.DATN.Bej.dto.response.cart.OrdersResponse;
import com.DATN.Bej.entity.cart.OrderItem;
import com.DATN.Bej.entity.cart.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderItems", ignore = true)
    Orders toOrder(OrderRequest request);
    @Mapping(target = "userName", source = "user.fullName")
    OrdersResponse toOrdersResponse(Orders orders);
    @Mapping(target = "userName", source = "user.fullName")
    OrderDetailsResponse toOrderDetailsResponse(Orders orders);

    @Mapping(target = "productA", ignore = true)
    OrderItem toOrderItem(OrderItemRequest request);
    @Mapping(target = "img", expression = "java(resolveImg(orderItem))")
    @Mapping(target = "productAttName", source = "productA.name")
    @Mapping(target = "color", source = "productA.variant.color")
    @Mapping(target = "productName", source = "productA.variant.product.name")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

    default String resolveImg(OrderItem orderItem) {
        if(orderItem.getProductA() == null
                || orderItem.getProductA().getVariant() == null
                || orderItem.getProductA().getVariant().getDetailImages() == null
                || orderItem.getProductA().getVariant().getDetailImages().isEmpty())
            return null;

        return orderItem.getProductA().getVariant().getDetailImages().getFirst().getUrl();
    }

}
