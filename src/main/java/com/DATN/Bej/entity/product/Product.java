package com.DATN.Bej.entity.product;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    String name;
    String description;
    LocalDate createDate;

    String image;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "sort_index")
    List<ProductImage> introImages;

    int status; // 0: ngung kinh doanh 1: dang ban

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "sort_index")
    List<ProductVariant> variants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "product_id")
    Category category;

}
