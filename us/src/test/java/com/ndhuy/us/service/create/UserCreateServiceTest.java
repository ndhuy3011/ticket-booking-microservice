package com.ndhuy.us.service.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import com.ndhuy.us.dao.UserDao;
import com.ndhuy.us.dto.UserDto.CreateInUserDto;
import com.ndhuy.us.dto.UserDto.CreateOutUserDto;
import com.ndhuy.us.entity.User;
import com.ndhuy.us.service.IRoleCreateService;
import com.ndhuy.us.valueobject.Password;
import com.ndhuy.us.valueobject.Username;

import lombok.extern.slf4j.Slf4j;



@Slf4j
class UserCreateServiceTest {

    private UserDao userDao;
    private IRoleCreateService roleCreateService;
    private UserCreate userCreate;

    @BeforeEach
    void setUp() {
        //Before each test, initialize mocks and the service under test
        userDao = mock(UserDao.class);
        roleCreateService = mock(IRoleCreateService.class);
        userCreate = new UserCreate();
        ReflectionTestUtils.setField(userCreate, "userDao", userDao);
        ReflectionTestUtils.setField(userCreate, "roleCreateService", roleCreateService);
    }

    @Test
    void testCreateUserCustomer_Success() {
        // Arrange
        String username = "testuser";
        String password = "testpass";
        CreateInUserDto input = mock(CreateInUserDto.class);
        when(input.username()).thenReturn(username);
        when(input.password()).thenReturn(password);

        User user = mock(User.class);
        when(user.getUsername()).thenReturn(username);

        when(userDao.insert(any(User.class))).thenReturn(user);

        // Act
        CreateOutUserDto result = userCreate.createUserCustomer(input);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.username());

        // Verify userDao.insert called with correct User
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).insert(userCaptor.capture());
        User insertedUser = userCaptor.getValue();
        assertEquals(Username.of(username), insertedUser.getUserId());
        assertTrue(Password.matches(password, insertedUser.getPwd().getValue()));

        // Verify roleCreateService.addRoleCustomer called with correct username
        verify(roleCreateService).addRoleCustomer(username);
    }

    @Test
    void testCreateUserCustomer_UserDaoThrowsException() {
        // Arrange
        String username = "user";
        String password = "pass";
        CreateInUserDto input = mock(CreateInUserDto.class);
        when(input.username()).thenReturn(username);
        when(input.password()).thenReturn(password);

        when(userDao.insert(any(User.class))).thenThrow(new RuntimeException("DB error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userCreate.createUserCustomer(input));
        verify(roleCreateService, never()).addRoleCustomer(anyString());
    }
}