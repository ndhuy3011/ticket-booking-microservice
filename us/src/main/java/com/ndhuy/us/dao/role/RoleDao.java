package com.ndhuy.us.dao.role;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ndhuy.us.dao.RoleDao;
import com.ndhuy.us.entity.Role;
import com.ndhuy.us.valueobject.RoleName;

import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class IRoleDao implements RoleDao {

    @Resource
    public RoleRepository roleRepository;

    public void checkNull(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role is null");
        }
        if (role.getRoleId() == null) {
            throw new IllegalArgumentException("Role name is null");
        }
    }

    /*
     * Validate before insert
     * 
     * @ndhuy3011
     * Parameter: role - role to validate
     * Return: void
     */

    public void beforeInsert(Role role) {

        checkNull(role);

        if (findByName(role.getRoleId().getValue()) != null) {
            throw new IllegalArgumentException("Role name already exists: " + role.getRoleId().getValue());
        }
    }

    /*
     * Insert role
     * 
     * @ndhuy3011
     * Parameter: role - role to insert
     * Return: inserted role
     */
    @Override
    public Role insert(Role role) {
        beforeInsert(role);
        return roleRepository.save(role);

    }

    /*
     * Update role
     * 
     * @ndhuy3011
     * Parameter: role - role to update
     * Return: updated role
     */
    @Override
    public Role update(Role role) {

        checkNull(role);

        if (findByName(role.getRoleId().getValue(), false) == null) {
            throw new IllegalArgumentException("Role name does not exist: " + role.getRoleId().getValue());
        }

        Role roleOld = lockByName(role.getRoleId().getValue());

        if (!Objects.equals(roleOld.getVersion(), role.getVersion())) {
            throw new IllegalArgumentException(
                    "Role was modified by another transaction: " + role.getRoleId().getValue());

        }

        roleOld.setRoleDesc(role.getRoleDesc());

        return roleRepository.save(roleOld);
    }

    /*
     * Find role by name
     * 
     * @ndhuy3011
     * Parameter: name - role name
     * Return: role
     */
    @Override
    public Role findByName(String name) {
        return findByName(name, false);
    }

    /*
     * Find role by name
     * 
     * @ndhuy3011
     * Parameter: name - role name
     * Parameter: exception - throw exception if true
     * Return: role
     */
    @Override
    public Role findByName(String name, boolean exception) {
        if (exception) {
            return roleRepository.findByRoleName(RoleName.of(name))
                    .orElseThrow(() -> new IllegalArgumentException("Role name not found: " + name));
        } else {
            return findByName(name);
        }
    }
    /*
     * Lock role by name
     * 
     * @ndhuy3011
     * Parameter: name - role name
     * Return: role
     */

    @Override
    public Role lockByName(String name) {
        return roleRepository.findByRoleName(RoleName.of(name))
                .orElseThrow(() -> new IllegalArgumentException("Username not found: " + name));
    }

}
