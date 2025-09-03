package com.ndhuy.us.dao.role_a_user;

import org.springframework.stereotype.Service;

import com.ndhuy.us.dao.RoleAUserDao;
import com.ndhuy.us.dao.RoleDao;
import com.ndhuy.us.dao.UserDao;
import com.ndhuy.us.entity.RoleAUser;
import com.ndhuy.us.entity.RoleAUser.RoleAUserId;

import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
 class IRoleAUserDao implements RoleAUserDao {

    @Resource
    RoleAUserRepository roleAUserRepository;

    @Resource
    RoleDao roleDao;

    @Resource
    UserDao userDao;

    /*
     * Check if roleAUser is null
     * 
     * @ndhuy3011
     * Parameter: roleAUser - roleAUser to check
     * Return: void
     */
    public void checkNull(RoleAUser roleAUser) {
        if (roleAUser == null) {
            throw new IllegalArgumentException("RoleAUser is null");
        }
        if (roleAUser.getRole() == null) {
            throw new IllegalArgumentException("Role is null");
        }
        if (roleAUser.getUser() == null) {
            throw new IllegalArgumentException("User is null");
        }
    }

    /*
     * Validate before insert
     * 
     * @ndhuy3011
     * Parameter: roleAUser - roleAUser to validate
     * Return: void
     */
    public void beforeInsert(RoleAUser roleAUser) {
        checkNull(roleAUser);
        roleAUserRepository.findById(roleAUser.getRoleAUserId()).ifPresent(e -> {
            throw new IllegalArgumentException(String.format("RoleAUser already exists: (roleName: %s, username: %s)",
                    roleAUser.getRoleAUserId().getRole(), roleAUser.getRoleAUserId().getUsername()));
        });
    }

    /*
     * Insert roleAUser
     * 
     * @ndhuy3011
     * Parameter: roleName - role name
     * Parameter: username - username
     * Return: void
     */
    @Override
    public RoleAUser insert(String roleName, String username) {

        log.info("Insert roleAUser: (roleName: {}, username: {})", roleName, username);
        var role = roleDao.findByName(roleName, true);

        var user = userDao.findByUsername(username, true);

        var roleAUser = RoleAUser.builder(user, role);

        beforeInsert(roleAUser);

        return roleAUserRepository.save(roleAUser);

    }

    /*
     * Delete roleAUser
     * 
     * @ndhuy3011
     * Parameter: roleName - role name
     * Parameter: username - username
     * Return: void
     */
    @Override
    public void delete(String roleName, String username) {
        log.info("Delete roleAUser: (roleName: {}, username: {})", roleName, username);
        var roleAUserId = RoleAUserId.builder(userDao.findByUsername(username).getUserId(),
                roleDao.findByName(roleName).getRoleId());

        roleAUserRepository.findById(roleAUserId).ifPresentOrElse(e -> roleAUserRepository.delete(e), () -> {
            throw new IllegalArgumentException(String.format("RoleAUser does not exist: (roleName: %s, username: %s)",
                    roleName, username));
        });
    }

}
