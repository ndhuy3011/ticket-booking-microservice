package com.ndhuy.us.valueobject;

public class RoleName implements java.io.Serializable {
    private String value;

    public RoleName(String roleName) {
        this.value = roleName;
    }

    public String getRoleName() {
        return value;
    }
}
