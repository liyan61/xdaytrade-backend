package com.cirko.xdaytrade.controller.user;

import org.springframework.web.bind.annotation.*;
import com.cirko.xdaytrade.entity.user.User;
import com.cirko.xdaytrade.helpers.response.Resp;
import com.cirko.xdaytrade.model.user.RequestLoginAndRegisterBean;
import com.cirko.xdaytrade.model.user.RequestUpdatePswBean;
import com.cirko.xdaytrade.modules.auth.annotation.CurrentUser;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Resp login(@RequestBody RequestLoginAndRegisterBean requestLoginBean) {
        if (requestLoginBean.getMode().equals("username")) {
            // TODO 用户名 + 密码登录
        } else if (requestLoginBean.getMode().equals("phone")) {
            // TODO 手机号快速登录（如果未注册自动注册）
        } else if (requestLoginBean.getMode().equals("email")) {
            // TODO 邮箱快速登录（如果未注册自动注册）
        }

        return Resp.success("login success");
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Resp register(@RequestBody RequestLoginAndRegisterBean requestRegisterBean) {
        if (requestRegisterBean.getMode().equals("username")) {
            // TODO 自定义用户名 + 密码
        } else if (requestRegisterBean.getMode().equals("phone")) {
            // TODO 手机号 + 短信验证码 + 密码
        } else if (requestRegisterBean.getMode().equals("email")) {
            // TODO 邮箱 + 邮件验证码 + 密码
        }

        return Resp.success("register success");
    }

    /**
     * 用户登出
     */
    @GetMapping("/logout")
    public Resp logout(@CurrentUser User user, HttpServletRequest request) {
        // TODO

        return Resp.success("register success");
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Resp updatePassword(@CurrentUser User user, @RequestBody RequestUpdatePswBean requestUpdatePswBean) {
        // TODO

        return Resp.success("updatePassword success");
    }

    /**
     * 获取手机短信验证码
     * 生成手机验证码保存到redis并发送短信给用户
     */
    @GetMapping("/phonecode")
    public Object sendPhonecode(String phone) throws Exception {
        return "";
    }

    /**
     * 获取邮箱验证码
     * 生成邮箱验证码保存到redis并发送邮件给用户
     */
    @GetMapping("/emailcode")
    public Object sendEmailcode(String email) throws Exception {
        return "";
    }
}
