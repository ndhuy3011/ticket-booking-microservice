package com.ndhuy.us.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndhuy.us.valueobject.RoleName;

public interface RoleRepository extends JpaRepository<com.ndhuy.us.entity.Role, RoleName> {

}
