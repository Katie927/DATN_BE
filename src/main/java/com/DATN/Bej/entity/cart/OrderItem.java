package com.DATN.Bej.entity.cart;

import com.DATN.Bej.entity.product.Product;
import com.DATN.Bej.entity.product.ProductAttribute;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Orders order;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    ProductAttribute productA;

    int quantity;

    @Column(nullable = false)
    double price;

}
