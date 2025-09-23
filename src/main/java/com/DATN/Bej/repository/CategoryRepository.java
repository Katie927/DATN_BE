package com.DATN.Bej.repository;

import com.DATN.Bej.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    boolean existsByName(String name);

}
