package com.DATN.Bej.entity.cart;

import com.DATN.Bej.entity.identity.User;
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
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String phoneNumber;
    String email;
    String address;
    LocalDate updatedAt;
    LocalDate orderAt;

    String description;

    double totalPrice;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems = new ArrayList<>();

    int type;
    int status;

}
