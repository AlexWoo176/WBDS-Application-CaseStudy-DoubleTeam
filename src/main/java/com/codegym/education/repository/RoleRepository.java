package com.codegym.education.repository;

import com.codegym.education.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String roleName);
}
