package com.DATN.Bej.entity.product;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    int type; // import / export

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    ProductAttribute productA;

    int quantity;
    double price;
}
