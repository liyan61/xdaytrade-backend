package com.cirko.xdaytrade.service.user;

import com.cirko.xdaytrade.entity.user.User;
import com.cirko.xdaytrade.entity.user.UserDetail;

import java.util.Optional;

public interface UserService {
    String login(String username,String pwd);
    String loginPhone(String phone,String code);
    String loginEmail(String email,String code);
    User register(String username,String pwd);
    User registerPhone(String phone,String pwd,String code);
    User registerEmail(String email,String pwd,String code);
    Optional<User> findOneById(Long id);
    User findOneByUsername(String username);
    User findOneByPhone(String phone);


}
