package com.cirko.xdaytrade.text;

import com.cirko.xdaytrade.entity.user.User;
import com.cirko.xdaytrade.model.user.RequestLoginAndRegisterBean;
import com.cirko.xdaytrade.modules.auth.annotation.CurrentUser;
import com.cirko.xdaytrade.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
public class LoginTest {

    @Autowired
    UserService userService;
    @Autowired(required = false)
    RequestLoginAndRegisterBean requestLoginBean;
    @Test
    public void x1(){

    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void x2(){
        String pwd=passwordEncoder.encode("123456Q");
        System.out.println(pwd);
    }
}
