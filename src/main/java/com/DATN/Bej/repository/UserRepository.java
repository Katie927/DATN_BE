package com.DATN.Bej.repository;

import com.DATN.Bej.entity.identity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByPhoneNumber(String email);
    Optional<User> findByPhoneNumber(String email);
}

