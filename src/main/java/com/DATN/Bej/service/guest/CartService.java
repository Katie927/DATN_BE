package com.DATN.Bej.service.guest;

import com.DATN.Bej.dto.request.cartRequest.OrderRequest;
import com.DATN.Bej.dto.response.cart.CartItemResponse;
import com.DATN.Bej.dto.response.cart.OrderDetailsResponse;
import com.DATN.Bej.dto.response.cart.OrdersResponse;
import com.DATN.Bej.entity.cart.CartItem;
import com.DATN.Bej.entity.cart.OrderItem;
import com.DATN.Bej.entity.cart.Orders;
import com.DATN.Bej.entity.identity.User;
import com.DATN.Bej.entity.product.ProductAttribute;
import com.DATN.Bej.exception.AppException;
import com.DATN.Bej.exception.ErrorCode;
import com.DATN.Bej.mapper.product.CartItemMapper;
import com.DATN.Bej.mapper.product.OrderMapper;
import com.DATN.Bej.repository.UserRepository;
import com.DATN.Bej.repository.product.CartItemRepository;
import com.DATN.Bej.repository.product.OrderRepository;
import com.DATN.Bej.repository.product.ProductAttributeRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class CartService {

    UserRepository userRepository;
    ProductAttributeRepository productAttributeRepository;
    CartItemRepository cartItemRepository;
    OrderRepository ordersRepository;

    OrderMapper orderMapper;
    CartItemMapper cartItemMapper;

    public CartItemResponse addToCart(String attId){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        log.info(name);
        User user = userRepository.findByPhoneNumber(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        ProductAttribute productA = productAttributeRepository.findById(attId).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHENTICATED));

        CartItem cartItem = cartItemRepository.findByUser_IdAndProductA_Id(user.getId(), productA.getId());

        if( cartItem == null){
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProductA(productA);
            cartItem.setColor(productA.getVariant().getColor());
            cartItem.setQuantity(1);
            cartItem.setPrice(productA.getFinalPrice());
            cartItem.setAddedAt(LocalDate.now());
            cartItem.setProductName(productA.getVariant().getProduct().getName());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        return cartItemMapper.toCartItemResponse(cartItemRepository.save(cartItem));
    }

    public List<CartItemResponse> viewCart(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        log.info(name);
        User user = userRepository.findByPhoneNumber(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return cartItemRepository.findAllByUserId(user.getId()).stream().map(cartItemMapper::toCartItemResponse).toList();
    }

    public OrderDetailsResponse placeOrder(OrderRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Orders orders = orderMapper.toOrder(request);
        orders.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        for (var itemReq : request.getItems()) {
            log.info(itemReq.getCartItemId());
            ProductAttribute productAtt = productAttributeRepository
                    .findById(itemReq.getProductAttId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            OrderItem orderItem = orderMapper.toOrderItem(itemReq);
            orderItem.setProductA(productAtt);
            orderItem.setOrder(orders);
            orderItem.setPrice(productAtt.getFinalPrice());
            cartItemRepository.deleteById(itemReq.getCartItemId());

            orderItems.add(orderItem);
        }
        double totalPrice = orderItems.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();

        orders.setTotalPrice(totalPrice);
        orders.setOrderItems(orderItems);
        orders.setOrderAt(LocalDate.now());

        Orders saved = ordersRepository.save(orders);
        return orderMapper.toOrderDetailsResponse(saved);
    }

    public List<OrderDetailsResponse> getMyOrder(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        log.info(name);
        User user = userRepository.findByPhoneNumber(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Orders> orders = ordersRepository.findAllByUserId(user.getId());

        return orders.stream().map(orderMapper::toOrderDetailsResponse).toList();
    }

    //  ======================================================================================
    public List<OrdersResponse> getAllOrders(){
        return ordersRepository.findAllByOrderByOrderAtDesc().stream().map(orderMapper::toOrdersResponse).toList();
    }

    public OrderDetailsResponse getOrderDetails(String orderId){
        return ordersRepository.findById(orderId).map(orderMapper::toOrderDetailsResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

}

