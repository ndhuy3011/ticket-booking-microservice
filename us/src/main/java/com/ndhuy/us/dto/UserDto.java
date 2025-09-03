package com.ndhuy.us.dto;

import com.ndhuy.us.valueobject.Password;
import com.ndhuy.us.valueobject.Username;

import jakarta.validation.constraints.NotNull;

public class UserDto {
    private UserDto() {
    }

    public record CreateInUserDto(@NotNull(message = Username.USERNAME_NOT_NULL_OR_EMPTY) String username,
            @NotNull(message = Password.PASSWORD_NOT_NULL_OR_EMPTY) String password) {

    }

    public record CreateOutUserDto(String username) {
    }
    public record InfoQueryInUserDto(String username) {
    }

    public record InfoQueryOutUserDto(String username,String role) {
    }
}
