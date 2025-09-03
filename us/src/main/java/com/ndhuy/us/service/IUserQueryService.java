package com.ndhuy.us.service;

import com.ndhuy.us.dto.UserDto.InfoQueryInUserDto;
import com.ndhuy.us.dto.UserDto.InfoQueryOutUserDto;
import com.ndhuy.us.dto.UserDto.LoginInUserDto;
import com.ndhuy.us.dto.UserDto.LoginOutUserDto;

public interface IUserQueryService {
    InfoQueryOutUserDto findByUsername(InfoQueryInUserDto username);
    LoginOutUserDto findByUsernameAndPassword(LoginInUserDto input);
}
