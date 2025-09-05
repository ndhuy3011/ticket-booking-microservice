package com.ndhuy.us.dao.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.ndhuy.us.entity.Role;
import com.ndhuy.us.valueobject.RoleName;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

interface RoleRepository extends JpaRepository<Role, RoleName> {
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<Role> findByRoleId(RoleName roleId);
}
