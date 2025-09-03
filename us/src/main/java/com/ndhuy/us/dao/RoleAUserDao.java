package com.ndhuy.us.dao;

import com.ndhuy.us.entity.RoleAUser;

public interface RoleAUserDao {
    public RoleAUser insert(String roleName, String username);
    public void delete(String roleName, String username);

}
