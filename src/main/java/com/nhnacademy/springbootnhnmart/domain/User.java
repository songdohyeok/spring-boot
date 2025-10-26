package com.nhnacademy.springbootnhnmart.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public enum Auth{
        ROLE_ADMIN, ROLE_CUSTOMER
    }
    private String id;
    private String password;
    private String name;
    private Auth auth;
}
