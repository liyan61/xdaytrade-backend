package com.cirko.xdaytrade.service.user;

import com.cirko.xdaytrade.entity.user.User;

import java.util.Optional;

public interface UserService {
    String login(String username, String pwd);
    Optional<User> findOneById(Long id);
    User findOneByUsername(String username);
    User findOneByPhone(String phone);
}
