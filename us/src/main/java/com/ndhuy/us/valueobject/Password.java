package com.ndhuy.us.valueobject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Password extends ValueObjectBase{

    private String value;
    private static final int BCRYPT_STRENGTH = 12;
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(BCryptVersion.$2Y,
            BCRYPT_STRENGTH);
    public static final String PASSWORD_NOT_NULL_OR_EMPTY = "Password is null or empty";

    public boolean matches(String rawPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, this.value);
    }

    private Password(String value) {
        this.value = value;
    }

    public static Password of(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException(PASSWORD_NOT_NULL_OR_EMPTY);
        }
        return new Password(bCryptPasswordEncoder.encode(password));
    }
}
