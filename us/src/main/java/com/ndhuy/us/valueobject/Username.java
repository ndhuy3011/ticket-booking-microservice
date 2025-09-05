package com.ndhuy.us.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Username extends ValueObjectBase  {

    private String value;
    public static final String USERNAME_NOT_NULL_OR_EMPTY = "Username is null or empty";

    private Username(String value) {
        this.value = value;
    }

    public static Username of(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException(USERNAME_NOT_NULL_OR_EMPTY);
        }
        return new Username(username);
    }
}
