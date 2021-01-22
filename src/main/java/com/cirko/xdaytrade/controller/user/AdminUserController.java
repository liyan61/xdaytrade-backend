package com.cirko.xdaytrade.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cirko.xdaytrade.service.user.UserService;

@RestController
@RequestMapping("/api/v1/admin/user")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
    @Autowired
    private UserService userService;

    //获取所有用户信息


    //根据手机返回某一个用户


    //根据用户名返回某一个用户


    //根据用户id返回某一个用户

}
