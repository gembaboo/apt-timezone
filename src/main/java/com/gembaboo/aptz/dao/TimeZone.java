package com.gembaboo.aptz.dao;

import java.io.Serializable;

public class TimeZone implements Serializable{
    private static final long serialVersionUID = 5496807185512943273L;


    private String value;

    public TimeZone() {
        super();
    }

    public TimeZone(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
