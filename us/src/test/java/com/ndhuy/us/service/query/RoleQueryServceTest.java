
package com.ndhuy.us.service.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ndhuy.us.constants.RoleConst;
import com.ndhuy.us.dao.RoleDao;
import com.ndhuy.us.dto.RoleDto.RoleQueryInput;
import com.ndhuy.us.dto.RoleDto.RoleQueryOut;
import com.ndhuy.us.entity.Role;
import com.ndhuy.us.valueobject.Description;
import com.ndhuy.us.valueobject.RoleName;



class RoleQueryServceTest {

    private RoleDao roleDao;
    private RoleQueryServce roleQueryServce;

    @BeforeEach
    void setUp() {
        roleDao = mock(RoleDao.class);
        roleQueryServce = new RoleQueryServce();
        // Inject mock
        roleQueryServce.roleDao = roleDao;
    }

    @Test
    void testGetRoleCustomer_ReturnsDefaultRole() {
        Role expectedRole = mock(Role.class);
        when(roleDao.findByName(RoleConst.DEFAULT_ROLE)).thenReturn(expectedRole);

        Role result = roleQueryServce.getRoleCustomer();

        assertSame(expectedRole, result);
        verify(roleDao).findByName(RoleConst.DEFAULT_ROLE);
    }

    @Test
    void testGetRole_ReturnsRoleQueryOut() {
        String roleName = "ADMIN";
        RoleQueryInput input = mock(RoleQueryInput.class);
        when(input.name()).thenReturn(roleName);

        RoleName roleId = mock(RoleName.class);
        Description roleDesc = mock(Description.class);
        when(roleId.getValue()).thenReturn(roleName);
        when(roleDesc.getValue()).thenReturn("Administrator");

        Role role = mock(Role.class);
        when(role.getRoleId()).thenReturn(roleId);
        when(role.getRoleDesc()).thenReturn(roleDesc);

        when(roleDao.findByName(roleName, true)).thenReturn(role);

        RoleQueryOut out = roleQueryServce.getRole(input);

        assertEquals(roleName, out.name());
        verify(roleDao).findByName(roleName, true);
    }
}