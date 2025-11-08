package com.DATN.Bej.entity.work;

import com.DATN.Bej.entity.identity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToMany
    Set<User> users;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    Shift shift;

    LocalDate workDate;

}
