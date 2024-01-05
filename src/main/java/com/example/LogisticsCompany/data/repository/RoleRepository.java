package com.example.LogisticsCompany.data.repository;

import com.example.LogisticsCompany.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String authority);
}