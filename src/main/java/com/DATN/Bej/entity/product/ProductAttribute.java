package com.DATN.Bej.entity.product;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "product_attribute")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
//    String value;

    double originalPrice;
    double finalPrice;
    double discount = 0;

    int stockQuantity = 0;
    int soldQuantity = 0;

    int status  = 0;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    ProductVariant variant;

}

