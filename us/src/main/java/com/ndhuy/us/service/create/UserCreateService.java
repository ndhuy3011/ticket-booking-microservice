package com.ndhuy.us.service.create;

import org.springframework.stereotype.Service;

import com.ndhuy.us.dao.UserDao;
import com.ndhuy.us.dto.UserDto.CreateInUserDto;
import com.ndhuy.us.dto.UserDto.CreateOutUserDto;
import com.ndhuy.us.entity.User;
import com.ndhuy.us.service.IRoleCreateService;
import com.ndhuy.us.service.IUserCreateService;
import com.ndhuy.us.valueobject.Password;
import com.ndhuy.us.valueobject.Username;

import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Transactional(isolation = org.springframework.transaction.annotation.Isolation.READ_COMMITTED)
@Service
@Slf4j
class UserCreate implements IUserCreateService {

    @Resource
    private UserDao userDao;

    @Resource
    private IRoleCreateService roleCreateService;

    /*
     * Create user customer
     * 
     * @ndhuy3011
     * Parameter: input - user create input
     * Return: UserCreateOut
     */
    @Override
    public CreateOutUserDto createUserCustomer(CreateInUserDto input) {
        log.info("Create user customer: {}", input);
        var user = userDao.insert(User.builder()
                .userId(Username.of(input.username()))
                .pwd(Password.of(input.password()))
                .build());
        roleCreateService.addRoleCustomer(user.getUsername());
        return new CreateOutUserDto(user.getUsername());
    }

}
