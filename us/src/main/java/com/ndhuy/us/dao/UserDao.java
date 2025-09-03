package com.ndhuy.us.dao;

import com.ndhuy.us.entity.User;

public interface UserDao {
    public User insert(User user);

    public User update(User user);

    public User findByUsername(String username);

    public User findByUsername(String username, boolean exception);

    public User lockByUsername(String username);
}
