package com.ndhuy.us.service.query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ndhuy.us.dao.UserDao;
import com.ndhuy.us.dto.UserDto.InfoQueryInUserDto;
import com.ndhuy.us.dto.UserDto.InfoQueryOutUserDto;
import com.ndhuy.us.service.IUserQueryService;

import jakarta.annotation.Resource;

@Transactional(readOnly = true, isolation = org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED)
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
