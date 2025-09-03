package com.ndhuy.us.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RoleName extends ValueObjectBase {
    private String value;

    private RoleName(String value) {
        this.value = value;
    }

    public static RoleName of(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Role name is null or empty");
        }
        return new RoleName(name);
    }
}
