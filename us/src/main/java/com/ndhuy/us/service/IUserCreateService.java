package com.ndhuy.us.service;

import com.ndhuy.us.dto.UserDto.CreateInUserDto;
import com.ndhuy.us.dto.UserDto.CreateOutUserDto;

public interface IUserCreateService {
    /*
     * Create user customer
     * 
     * @ndhuy3011
     * Parameter: input - user create input
     * Return: UserCreateOut
     */
    public CreateOutUserDto createUserCustomer(CreateInUserDto input);
}
