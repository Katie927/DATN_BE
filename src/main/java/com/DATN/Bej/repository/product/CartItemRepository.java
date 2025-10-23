package com.DATN.Bej.repository.product;

import com.DATN.Bej.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

    List<CartItem> findAllByUserId(String userId);
    CartItem findByUser_IdAndProductA_Id(String userId, String attributeId);

}
