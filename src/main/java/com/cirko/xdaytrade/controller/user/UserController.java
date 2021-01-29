package com.cirko.xdaytrade.controller.user;

import com.cirko.xdaytrade.entity.user.User;
import com.cirko.xdaytrade.helpers.response.Resp;
import com.cirko.xdaytrade.modules.auth.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cirko.xdaytrade.model.user.RequestUserDetailBean;
import com.cirko.xdaytrade.service.user.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取当前登录的用户信息
     */
    @GetMapping(path = "/current")
    public Resp getCurrentUser(@CurrentUser User user) {
        return Resp.success(user);
    }

    /**
     * 更新用户的详细信息
     */
    @PutMapping(path = "/current")
    public Resp updateCurrentUser(@CurrentUser User user, @RequestBody RequestUserDetailBean requestUserDetailBean) {
        return Resp.success("success");
    }
}
