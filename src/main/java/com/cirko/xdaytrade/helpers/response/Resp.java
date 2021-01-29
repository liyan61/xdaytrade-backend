package com.cirko.xdaytrade.helpers.response;

import java.util.HashMap;

public class Resp {
    private int code;
    private String msg;
    private Object data;

    public static Resp success(Object data){
        return new Resp(CodeMsg.SUCCESS, data);
    }

    public static Resp error(CodeMsg codeMsg){
        return new Resp(codeMsg, new HashMap<>());
    }

    private Resp(CodeMsg codeMsg, Object data) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
