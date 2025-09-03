package com.ndhuy.us.service.query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ndhuy.us.dao.UserDao;
import com.ndhuy.us.dto.UserDto.InfoQueryInUserDto;
import com.ndhuy.us.dto.UserDto.InfoQueryOutUserDto;
import com.ndhuy.us.service.IUserQueryService;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

@Transactional
@Service
class UserQueryService implements IUserQueryService {

    @Resource
    UserDao userDao;

    @Override
    public InfoQueryOutUserDto findByUsername(InfoQueryInUserDto input) {
        var user = userDao.findByUsername(input.username(), true);

        return new InfoQueryOutUserDto(user.getUsername(), StringUtils.join(",", user.getAuthorities()));

    }

}
