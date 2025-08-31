package com.ndhuy.us.valueobject;

import java.io.Serializable;

public class Password  implements Serializable {
    private String value;

    public Password(String password) {
        this.value = password;
    }

    public String getPassword() {
        return value;
    }
}
