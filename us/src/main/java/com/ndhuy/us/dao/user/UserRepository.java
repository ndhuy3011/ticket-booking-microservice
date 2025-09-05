package com.ndhuy.us.dao.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.ndhuy.us.entity.User;
import com.ndhuy.us.valueobject.Username;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

interface UserRepository extends JpaRepository<User, Username> {
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<User> findByUserId(Username username);
}
