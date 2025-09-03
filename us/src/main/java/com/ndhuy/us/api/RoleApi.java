package com.ndhuy.us.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ndhuy.us.service.IRoleCreateService;

import jakarta.annotation.Resource;


@RestController
@RequestMapping("/api/role")
public class RoleApi {
    
    @Resource IRoleCreateService roleCreateService;

    
}
