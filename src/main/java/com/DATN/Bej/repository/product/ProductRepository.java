package com.DATN.Bej.repository.product;

import com.DATN.Bej.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository <Product, String> {
    boolean existsByName(String name);
    List<Product> findAllByOrderByCreateDateDesc();
    List<Product> findByStatusOrderByCreateDateDesc(int status);

    Optional<Product> findByName(String productName);
}
