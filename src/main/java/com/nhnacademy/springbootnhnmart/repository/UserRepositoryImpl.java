package com.nhnacademy.springbootnhnmart.repository;

import com.nhnacademy.springbootnhnmart.domain.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    public UserRepositoryImpl() {
        User customer1 = new User();
        customer1.setId("customer1");
        customer1.setPassword("123");
        customer1.setName("고객1");
        customer1.setAuth(User.Auth.ROLE_CUSTOMER);

        userMap.put("customer1", customer1);

        User customer2 = new User();
        customer2.setId("customer2");
        customer2.setPassword("123");
        customer2.setName("고객2");
        customer2.setAuth(User.Auth.ROLE_CUSTOMER);

        userMap.put("customer2", customer2);

        User admin1 = new User();
        admin1.setId("admin1");
        admin1.setPassword("123");
        admin1.setName("관리자1");
        admin1.setAuth(User.Auth.ROLE_ADMIN);

        userMap.put("admin1", admin1);

        User admin2 = new User();
        admin2.setId("admin2");
        admin2.setPassword("123");
        admin2.setName("관리자2");
        admin2.setAuth(User.Auth.ROLE_ADMIN);

        userMap.put("admin2", admin2);
    }

    @Override
    public User getUser(String id) {
        return userMap.get(id);
    }

    @Override
    public boolean matches(String id, String pw) {
        User user = getUser(id);
        return (user != null && user.getPassword().equals(pw));
    }
}
