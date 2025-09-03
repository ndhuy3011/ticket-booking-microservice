package com.ndhuy.us.service.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ndhuy.us.constants.RoleConst;
import com.ndhuy.us.dao.RoleDao;
import com.ndhuy.us.dto.RoleDto.RoleQueryInput;
import com.ndhuy.us.dto.RoleDto.RoleQueryOut;
import com.ndhuy.us.entity.Role;
import com.ndhuy.us.service.IRoleQueryService;

import jakarta.annotation.Resource;

@Service
@Transactional(readOnly = true,isolation = org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED)
class RoleQueryServce  implements IRoleQueryService{
    @Resource RoleDao roleDao;

    /*
     * Get default role
     * 
     * @ndhuy3011
     * Parameter: void
     * Return: default role
     */
    @Override
    public Role getRoleCustomer() {
       return roleDao.findByName(RoleConst.DEFAULT_ROLE);
    }

    @Override
    public RoleQueryOut getRole(RoleQueryInput input) {
        var role = roleDao.findByName(input.name(),true);
        return new RoleQueryOut(role.getRoleId().getValue(), role.getRoleDesc().getValue());
    }
}
