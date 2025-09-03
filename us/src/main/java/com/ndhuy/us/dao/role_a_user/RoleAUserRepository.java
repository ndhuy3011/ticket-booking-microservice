package com.ndhuy.us.dao.role_a_user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndhuy.us.entity.RoleAUser;
import com.ndhuy.us.entity.RoleAUser.RoleAUserId;

 interface RoleAUserRepository extends JpaRepository<RoleAUser, RoleAUserId> {
    
}
