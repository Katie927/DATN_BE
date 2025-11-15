package com.DATN.Bej.entity.cart;

import com.DATN.Bej.entity.identity.User;
import com.DATN.Bej.entity.product.ProductAttribute;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    ProductAttribute productA;
    int quantity;
    @Column(nullable = false)
    double price;
    LocalDate addedAt;

    String color;
    String productName;
}
