package com.DATN.Bej.repository.product;

import com.DATN.Bej.entity.cart.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {

    List<Orders> findAllByUserId(String userId);
    List<Orders> findAllByOrderByOrderAtDesc();

}
