package com.cirko.xdaytrade.helpers.response;

public class CodeMsg {
    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");

    //登录模块 5002XX
    public static CodeMsg NO_PERMISSION = new CodeMsg(500200, "无权限");
    public static CodeMsg NOT_LOGGED_IN = new CodeMsg(500210, "用户未登陆");
    public static CodeMsg INVALID_TOKEN = new CodeMsg(500220, "无效的 token: %s");
    public static CodeMsg NO_SUCH_USER = new CodeMsg(500250, "用户 %s 不存在");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500212, "登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    public static CodeMsg PRIMARY_ERROR = new CodeMsg(500216, "主键冲突");

    //注册模块
    public static CodeMsg SUCH_USER = new CodeMsg(500217, "用户名已存在");
    public static CodeMsg MOBILE_EXIST = new CodeMsg(500218, "手机号已注册");
    public static CodeMsg EMAIL_ERROR = new CodeMsg(500219, "邮箱格式错误");
    public static CodeMsg EMAIL_EXIST = new CodeMsg(500225, "邮箱已注册");

    //商品模块 5003XX

    //订单模块 5004XX
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");

    /**
     * 返回带参数的错误码
     * @param args fillParams
     * @return CodeMessage
     */
    public CodeMsg fill(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);

        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMessage [code=" + code + ", msg=" + msg + "]";
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
