package com.ndhuy.us.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ndhuy.us.annotation.PublicEndpoint;
import com.ndhuy.us.dto.UserDto.CreateInUserDto;
import com.ndhuy.us.dto.UserDto.CreateOutUserDto;
import com.ndhuy.us.dto.UserDto.InfoQueryInUserDto;
import com.ndhuy.us.dto.UserDto.InfoQueryOutUserDto;
import com.ndhuy.us.dto.UserDto.LoginInUserDto;
import com.ndhuy.us.dto.UserDto.LoginOutUserDto;
import com.ndhuy.us.service.IUserCreateService;
import com.ndhuy.us.service.IUserQueryService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/v1/users")
public class UserApi {
    @Resource
    IUserCreateService userCreateService;
    @Resource
    IUserQueryService userQueryService;

    @PostMapping("/register")
    @PublicEndpoint
    public ResponseEntity<CreateOutUserDto> postCreateUser(@RequestBody CreateInUserDto entity) {
        return new ResponseEntity<>(userCreateService.createUserCustomer(entity), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginOutUserDto> postUsernameAndPassword(@RequestBody LoginInUserDto entity) {
        return new ResponseEntity<>(userQueryService.findByUsernameAndPassword(entity), HttpStatus.OK);
    }
    

    @PostMapping("/info")
    public ResponseEntity<InfoQueryOutUserDto> postGetUser(@RequestBody InfoQueryInUserDto entity) {
        return new ResponseEntity<>(userQueryService.findByUsername(entity), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<InfoQueryOutUserDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        var entity = new InfoQueryInUserDto(userDetails.getUsername());
        return new ResponseEntity<>(userQueryService.findByUsername(entity), HttpStatus.OK);
    }

}
