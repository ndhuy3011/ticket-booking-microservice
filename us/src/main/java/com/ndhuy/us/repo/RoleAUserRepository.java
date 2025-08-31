package com.ndhuy.us.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndhuy.us.entity.RoleAUser;
import com.ndhuy.us.entity.RoleAUser.RoleAUserId;

public interface RoleAUserRepository extends JpaRepository<RoleAUser, RoleAUserId> {
    
}
