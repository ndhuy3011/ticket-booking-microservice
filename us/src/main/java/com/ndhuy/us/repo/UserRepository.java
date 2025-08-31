package com.ndhuy.us.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndhuy.us.entity.User;
import com.ndhuy.us.valueobject.Username;

public interface UserRepository extends JpaRepository<User,Username>{
    
}
