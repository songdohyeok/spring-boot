package com.nhnacademy.springbootnhnmart;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.springbootnhnmart.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRepositoryImplTest {

    UserRepositoryImpl repo;

    @BeforeEach
    void setUp() {
        repo = new UserRepositoryImpl();
    }

    @Test
    void loginPassTest() {
        assertTrue(repo.matches("customer1", "123"));
        assertTrue(repo.matches("admin1", "123"));
    }

    @Test
    void loginFailTest() {
        assertFalse(repo.matches("qwe", "123"));
        assertFalse(repo.matches("customer1", "qwe"));
    }
}
