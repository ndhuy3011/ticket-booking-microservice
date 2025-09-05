package com.ndhuy.us.service.query;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ndhuy.us.dao.UserDao;
import com.ndhuy.us.dto.UserDto.InfoQueryInUserDto;
import com.ndhuy.us.dto.UserDto.InfoQueryOutUserDto;
import com.ndhuy.us.dto.UserDto.LoginInUserDto;
import com.ndhuy.us.dto.UserDto.LoginOutUserDto;
import com.ndhuy.us.entity.User;





class UserQueryServiceTest {

    private UserDao userDao;
    private UserQueryService userQueryService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        userQueryService = new UserQueryService();
        // Inject mock
        userQueryService.userDao = userDao;
    }

    @Test
    void testFindByUsername_ReturnsCorrectDto() {
        String username = "testuser";
        String[] authorities = {"ROLE_USER", "ROLE_ADMIN"};
        User user = mock(User.class);
        when(user.getUsername()).thenReturn(username);
        when(user.getAuthorities().toArray()).thenReturn(authorities);

        when(userDao.findByUsername(username, true)).thenReturn(user);

        InfoQueryInUserDto input = mock(InfoQueryInUserDto.class);
        when(input.username()).thenReturn(username);

        InfoQueryOutUserDto result = userQueryService.findByUsername(input);

        assertEquals(username, result.username());
        assertEquals(StringUtils.join(",", authorities),result.role());
    }

    @Test
    void testFindByUsernameAndPassword_ValidPassword_ReturnsDto() {
        String username = "testuser";
        String password = "testpass";
        String[] authorities = {"ROLE_USER"};
        User user = mock(User.class);
        when(user.getUsername()).thenReturn(username);
        when(user.getAuthorities().toArray()).thenReturn(authorities);
        when(user.getPassword()).thenReturn(password);

        when(userDao.findByUsername(username, true)).thenReturn(user);

        LoginInUserDto input = mock(LoginInUserDto.class);
        when(input.username()).thenReturn(username);
        when(input.password()).thenReturn(password);

        // Simulate password matches
        when(user.getPwd().matches(password)).thenReturn(false);

        LoginOutUserDto result = userQueryService.findByUsernameAndPassword(input);

        assertEquals(username, result.username());
        assertEquals(StringUtils.join(",", authorities), result.role());
    }

    @Test
    void testFindByUsernameAndPassword_InvalidPassword_ThrowsException() {
        String username = "testuser";
        String password = "testpass";
        String wrongPassword = "wrong";
        User user = mock(User.class);
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(password);

        when(userDao.findByUsername(username, true)).thenReturn(user);

        LoginInUserDto input = mock(LoginInUserDto.class);
        when(input.username()).thenReturn(username);
        when(input.password()).thenReturn(wrongPassword);

        // Simulate password does not match
        when(user.getPwd().matches(wrongPassword)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            userQueryService.findByUsernameAndPassword(input);
        });
    }
}