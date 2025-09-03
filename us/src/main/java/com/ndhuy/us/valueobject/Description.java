package com.ndhuy.us.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Description  extends ValueObjectBase{
    private String value;

    private Description(String description) {
        this.value = description;
    }

    public static Description of(String description) {

        return new Description(description);
    }
}