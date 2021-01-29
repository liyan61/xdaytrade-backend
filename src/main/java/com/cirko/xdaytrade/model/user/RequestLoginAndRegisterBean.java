package com.cirko.xdaytrade.model.user;

/**
 * 注册模式：
 * 1. 手机号 + 短信验证码 + 密码
 * 2. 邮箱 + 邮件验证码 + 密码
 * 3. 自定义用户名 + 密码
 *
 * 其中
 * 使用手机号或者邮箱注册的账号，username 默认为手机号或者邮箱
 * 方便下次直接使用 username + password 的方式登录
 *
 * 用户登录有两种模式：
 * 1. 需要密码：使用 `用户名+密码` 的方式登录（用户名可以是自定义用户名、手机号或邮箱，看当时用户注册的时候使用了什么进行注册）
 * 2. 不需要密码，快速登录：使用 `手机号/邮箱+验证码` 的方式登录
 *    2.1: 未注册：初始化一串随机密码，username 默认为 手机号/邮箱
 *    2.2: 已注册：直接登录
 *
 */
public class RequestLoginAndRegisterBean {
    /**
     * 用户名
     */
    String username;

    /**
     * 密码
     */
    String password;

    /**
     * 手机号
     */
    String phone;

    /**
     * 短信验证码
     */
    String code;

    /**
     * 注册/登录模式：
     * phone:
     *      注册：手机号（phone） + 短信验证码（code） + 密码（password）
     *      登录：手机号（phone） + 短信验证码（code） 快速登录
     * emial:
     *      注册：邮箱（email） + 邮件验证码（code） + 密码（password）
     *      登录：邮箱（email） + 邮件验证码（code） 快速登录
     * username:
     *      注册：自定义用户名（username） + 密码（password）
     *      登录：自定义用户名/手机号/邮箱（username） + 密码（password）
     */
    String mode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
