package com.ndhuy.us.valueobject;


public class Username implements java.io.Serializable {
    private String value;

    public Username(String username) {
        this.value = username;
    }

    public String getUsername() {
        return value;
    }
}
