package com.nhnacademy.springbootnhnmart.repository;

import com.nhnacademy.springbootnhnmart.domain.User;

public interface UserRepository {
    User getUser(String id);

    boolean matches(String id, String password);
}
