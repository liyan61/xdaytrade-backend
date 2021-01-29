package com.cirko.xdaytrade.controller.user;

import com.cirko.xdaytrade.dao.user.UserRepository;
import com.cirko.xdaytrade.service.user.UserService;
import com.cirko.xdaytrade.utils.EmailUtil;
import com.cirko.xdaytrade.utils.SendSMSUtil;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.cirko.xdaytrade.entity.user.User;
import com.cirko.xdaytrade.helpers.response.Resp;
import com.cirko.xdaytrade.model.user.RequestLoginAndRegisterBean;
import com.cirko.xdaytrade.model.user.RequestUpdatePswBean;
import com.cirko.xdaytrade.modules.auth.annotation.CurrentUser;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.cirko.xdaytrade.helpers.response.CodeMsg.SUCCESS;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired(required = false)
    UserDetails userDetails;
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Resp login(@RequestBody RequestLoginAndRegisterBean requestLoginBean) {
        String token="";
        if (requestLoginBean.getMode().equals("username")) {
            // TODO 用户名 + 密码登录
             token = userService.login(requestLoginBean.getUsername(), requestLoginBean.getPassword());
        } else if (requestLoginBean.getMode().equals("phone")) {
            // TODO 手机号快速登录（如果未注册自动注册）
             token=userService.loginPhone(requestLoginBean.getPhone(),requestLoginBean.getCode());
        } else if (requestLoginBean.getMode().equals("email")) {
            // TODO 邮箱快速登录（如果未注册自动注册）
            token=userService.loginEmail(requestLoginBean.getEmail(),requestLoginBean.getCode());
        }
        return Resp.success(token);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Resp register(@RequestBody RequestLoginAndRegisterBean requestRegisterBean) {
        if (requestRegisterBean.getMode().equals("username")) {
            // TODO 自定义用户名 + 密码
            userService.register(requestRegisterBean.getUsername(),requestRegisterBean.getPassword());
        } else if (requestRegisterBean.getMode().equals("phone")) {
            // TODO 手机号 + 短信验证码 + 密码
            userService.registerPhone(requestRegisterBean.getPhone(),requestRegisterBean.getPassword(),requestRegisterBean.getCode());
        } else if (requestRegisterBean.getMode().equals("email")) {
            // TODO 邮箱 + 邮件验证码 + 密码
            userService.registerEmail(requestRegisterBean.getEmail(),requestRegisterBean.getPassword(),requestRegisterBean.getCode());
        }
        return Resp.success("register success");
    }

    /**
     * 用户登出
     */
    @GetMapping("/logout")
    public Resp logout(@CurrentUser User user, HttpServletRequest request) {
        // TODO
        String token = request.getHeader("token");
        


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
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/phonecode")
    public Object sendPhonecode(String phoneNumber) throws Exception {
        //发送短信
        String result = SendSMSUtil.sendSmsUtil(phoneNumber);
        if (result == null || !result.equals("OK")) {// 发送不成功
            return "0";
        }
        // 获取验证码
        int code = SendSMSUtil.getCode();
        Map<String,Object> map=new HashMap<>();
        // 将数据存入redis
        map.put(phoneNumber,code+"");
        //用phoneNumber来做键，可以做到唯一性
        redisTemplate.opsForHash().putAll(phoneNumber,map);
        // 设置redis过期时间,这个时间是秒为单位的，设置5分钟之内有效，过了就会自动删除
        redisTemplate.expire(phoneNumber, 60*5, TimeUnit.SECONDS);
        return "OK";
    }

    /**
     * 获取邮箱验证码
     * 生成邮箱验证码保存到redis并发送邮件给用户
     */
    @Autowired(required = false)
    EmailUtil emailUtil;

    @GetMapping("/emailcode")
    public Object sendEmailcode(String email) throws Exception {
        String message= emailUtil.sendEmail(email);
        if (message == null || !message.equals("OK")) {// 发送不成功
            return "0";
        }
        // 获取验证码
        String code = emailUtil.getVCode();
        Map<String,Object> map1=new HashMap<>();
        // 将数据存入redis
        map1.put(email,code+"");
        redisTemplate.opsForHash().putAll(email,map1);
        // 设置redis过期时间,这个时间是秒为单位的，设置5分钟之内有效，过了就会自动删除
        redisTemplate.expire(email, 60*5, TimeUnit.SECONDS);
        return "OK";
    }
}
