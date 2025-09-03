package com.ndhuy.us.dao;

import com.ndhuy.us.entity.Role;

public interface RoleDao {
    
    public Role insert(Role role);
    
    public Role update( Role role);

    public Role findByName(String name);

    public Role findByName(String name, boolean exception);

    public Role lockByName(String name);
}
