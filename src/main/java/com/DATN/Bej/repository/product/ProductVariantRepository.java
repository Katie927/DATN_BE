package com.DATN.Bej.repository.product;

import com.DATN.Bej.entity.product.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {
}
