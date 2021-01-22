package com.cirko.xdaytrade.helpers.exception;

import com.cirko.xdaytrade.helpers.response.CodeMsg;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cirko.xdaytrade.helpers.response.Resp;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 自定义全局异常拦截器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    // 拦截所有异常
    @ExceptionHandler(value = Exception.class)
    public Resp exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();

        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;

            return Resp.error(ex.getCodeMsg());
        } else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            // 绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();

            // 给状态码填充参数
            return Resp.error(CodeMsg.BIND_ERROR.fill(msg));
        } else {
            return Resp.error(CodeMsg.SERVER_ERROR);
        }
    }
}
