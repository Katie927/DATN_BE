package com.DATN.Bej.repository;

import com.DATN.Bej.entity.identity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

}
