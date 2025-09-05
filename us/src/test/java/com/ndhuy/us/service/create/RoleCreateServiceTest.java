package com.ndhuy.us.service.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ndhuy.us.constants.RoleConst;
import com.ndhuy.us.dao.RoleAUserDao;
import com.ndhuy.us.dao.RoleDao;
import com.ndhuy.us.entity.Role;
import com.ndhuy.us.valueobject.Description;
import com.ndhuy.us.valueobject.RoleName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class RoleCreateServiceTest {

    @Mock
    private RoleDao roleDao;

    @Mock
    private RoleAUserDao roleAUserDao;

    @InjectMocks
    private RoleCreateService roleCreateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRoleCustomer_shouldCreateDefaultRoleAndAddToUser_whenRoleDoesNotExist() {
        String userId = "testuser";
        when(roleDao.findByName(RoleConst.DEFAULT_ROLE)).thenReturn(null);

        roleCreateService.addRoleCustomer(userId);

        // Verify default role creation
        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleDao).insert(roleCaptor.capture());
        Role insertedRole = roleCaptor.getValue();
        log.info("Inserted Role: {}", insertedRole);
        assertNotNull(insertedRole);

        assertEquals(RoleName.of(RoleConst.DEFAULT_ROLE), insertedRole.getRoleId());
        assertEquals(Description.of(RoleConst.DEFAULT_ROLE), insertedRole.getRoleDesc());
        // Verify role assigned to user
        verify(roleAUserDao).insert(RoleConst.DEFAULT_ROLE, userId);
    }

    @Test
    void addRoleCustomer_shouldNotCreateDefaultRole_whenRoleAlreadyExists() {
        String userId = "testuser";
        Role existingRole = mock(Role.class);
        when(roleDao.findByName(RoleConst.DEFAULT_ROLE)).thenReturn(existingRole);

        roleCreateService.addRoleCustomer(userId);

        // Should not insert a new role
        verify(roleDao, never()).insert(any(Role.class));
        // Should still assign role to user
        verify(roleAUserDao).insert(RoleConst.DEFAULT_ROLE, userId);
    }
}