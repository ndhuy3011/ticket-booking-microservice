package com.ndhuy.us.service;

import com.ndhuy.us.dto.UserDto.InfoQueryInUserDto;
import com.ndhuy.us.dto.UserDto.InfoQueryOutUserDto;

public interface IUserQueryService {
    InfoQueryOutUserDto findByUsername(InfoQueryInUserDto username);
}
