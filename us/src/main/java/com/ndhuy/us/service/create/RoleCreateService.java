package com.ndhuy.us.service.create;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ndhuy.us.constants.RoleConst;
import com.ndhuy.us.dao.RoleAUserDao;
import com.ndhuy.us.dao.RoleDao;
import com.ndhuy.us.entity.Role;
import com.ndhuy.us.service.IRoleCreateService;
import com.ndhuy.us.valueobject.Description;
import com.ndhuy.us.valueobject.RoleName;

import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(isolation = org.springframework.transaction.annotation.Isolation.READ_COMMITTED)
@Slf4j
class RoleCreateService implements IRoleCreateService {
    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleAUserDao roleAUserDao;

    /*
     * Create default role if not exists
     * 
     * @ndhuy3011
     * Parameter: void
     * Return: void
     */
    private void roleDefault() {
        var role = roleDao.findByName(RoleConst.DEFAULT_ROLE);
        if (Objects.isNull(role)) {
            role = new Role();
            role.setRoleId(RoleName.of(RoleConst.DEFAULT_ROLE));
            role.setRoleDesc(Description.of(RoleConst.DEFAULT_ROLE));
            roleDao.insert(role);
        }
    }

    /*
     * Add default role to user
     * 
     * @ndhuy3011
     * Parameter: userId - user id to add role
     * Return: void
     */
    @Override
    public void addRoleCustomer(String userId) {
        log.info("Add role customer to userId: {}", userId);
        roleDefault();
        roleAUserDao.insert(RoleConst.DEFAULT_ROLE, userId);

    }

    

}
