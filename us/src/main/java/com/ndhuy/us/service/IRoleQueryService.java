package com.ndhuy.us.service;

import com.ndhuy.us.dto.RoleDto.RoleQueryInput;
import com.ndhuy.us.dto.RoleDto.RoleQueryOut;
import com.ndhuy.us.entity.Role;

public interface IRoleQueryService {
    /*
     * Get default role
     * 
     * @ndhuy3011
     * Parameter: void
     * Return: default role
     */
    Role getRoleCustomer();
    RoleQueryOut getRole(RoleQueryInput input);
}
